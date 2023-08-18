package com.bzcode.simplemvvm.domain.utils.api_util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;


import com.bzcode.simplemvvm.domain.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Common class used by API responses.
 * @param <T>
 */
public class ApiResponse<T> {

    private static final Pattern LINK_PATTERN = Pattern
            .compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");
    private static final Pattern PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)");
    private static final String NEXT_LINK = "next";
    public int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;
    @NonNull
    private final Map<String, String> links;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
        links = Collections.emptyMap();

        Utility.logger("API Response Error : "+errorMessage);
    }

    public ApiResponse(Response<T> response) {

        Utility.logger("URL : " + response.raw().request().url());

        if(response.isSuccessful()) {
            code = response.code();
            Utility.logger("ApiResponse Successful.");
            body = response.body();
            errorMessage = null;
        } else {
            Utility.logger("ApiResponse Something wrong.");
            String message = null;

            try {
                ResponseBody responseBody = response.errorBody();
                if(responseBody != null) {
                    message = responseBody.string();
                    try {
                        JSONObject jObjError = new JSONObject(message);
                        Utility.logger("API Error Response : " + jObjError.getString("message"));
                        message = jObjError.getString("message");

                        if(message.contains("##")) {
                            String[] messageDataList = message.split("##");
                            if(messageDataList.length > 1) {
                                code = Integer.parseInt(messageDataList[0]);
                                message = messageDataList[1];
                            }
                        }

                    } catch (JSONException e) {
                        Utility.logError("JSON Parsing error.", e);
                    }
                }

            } catch (NullPointerException ne) {
                Utility.logError("Null Pointer Exception.", ne);
            }catch (IOException ignored) {

                Utility.logError("error while parsing response", ignored);
            }

            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }

            if(code == 0) {
                code = response.code();
            }

            errorMessage = message;
            body = null;
        }
        String linkHeader = response.headers().get("link");
        if (linkHeader == null) {
            links = Collections.emptyMap();
        } else {
            links = new ArrayMap<>();
            Matcher matcher = LINK_PATTERN.matcher(linkHeader);

            while (matcher.find()) {
                int count = matcher.groupCount();
                if (count == 2) {
                    links.put(matcher.group(2), matcher.group(1));
                }
            }
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public Integer getNextPage() {
        String next = links.get(NEXT_LINK);
        if (next == null) {
            return null;
        }
        Matcher matcher = PAGE_PATTERN.matcher(next);
        if (!matcher.find() || matcher.groupCount() != 1) {
            return null;
        }
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
            Utility.logger("cannot parse next page from %s");
            return null;
        }
    }
}

