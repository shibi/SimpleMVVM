package com.bzcode.simplemvvm.data.remote.api;

import com.bzcode.simplemvvm.data.remote.dto.login.LoginResponse;
import com.bzcode.simplemvvm.data.remote.dto.register.RegisterResponse;
import com.bzcode.simplemvvm.data.remote.request.login.LoginRequest;
import com.bzcode.simplemvvm.data.remote.request.register.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("mockLogin")
    Call<LoginResponse> requestLogin(@Body LoginRequest request);

    @POST("mockRegister")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

}
