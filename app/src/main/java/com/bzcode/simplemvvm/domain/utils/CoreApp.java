package com.bzcode.simplemvvm.domain.utils;

import android.app.Application;

import com.bzcode.simplemvvm.data.remote.api.ApiGenerator;
import com.bzcode.simplemvvm.data.remote.api.ApiService;

public class CoreApp extends Application {

    public ApiService getWebService(){
        ApiService apiService = ApiGenerator.createApiService(ApiService.class, Config.ENCRYPTED_API_KEY);
        return apiService;
    }

    public AppExecutors getAppExecutors(){
        AppExecutors appExecutors = new AppExecutors();
        return appExecutors;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
