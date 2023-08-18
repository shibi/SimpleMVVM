package com.bzcode.simplemvvm.domain.repository;


import androidx.lifecycle.LiveData;
import com.bzcode.simplemvvm.data.remote.dto.login.User;
import com.bzcode.simplemvvm.data.remote.dto.register.RegisterResponse;
import com.bzcode.simplemvvm.domain.utils.api_util.Resource;

/**
 * @author Shibin
 * created on 01-08-2023 at 10:40
 */
public interface IEmployeeRepository {

    LiveData<Resource<User>> requestLogin(String email, String phone);

    LiveData<Resource<RegisterResponse>> registerUser(String name, String email, String phone);

}
