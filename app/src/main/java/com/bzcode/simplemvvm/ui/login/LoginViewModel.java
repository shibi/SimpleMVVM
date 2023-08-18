package com.bzcode.simplemvvm.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.bzcode.simplemvvm.data.remote.dto.login.User;
import com.bzcode.simplemvvm.domain.repository.IEmployeeRepository;
import com.bzcode.simplemvvm.domain.utils.api_util.Resource;

/**
 * @author Shibin
 * created on 01-08-2023 at 12:05
 */
public class LoginViewModel extends ViewModel {

    private IEmployeeRepository repository;

    //Login
    private LiveData<Resource<User>> loginLiveData;
    private MutableLiveData<LoginParams> loginMutableLiveData = new MutableLiveData<>();



    public LoginViewModel(IEmployeeRepository repository){
        this.repository = repository;

        //Login
        loginLiveData = Transformations.switchMap(loginMutableLiveData, obj ->{
            if(obj==null){
                return new MutableLiveData<>();
            }
            return repository.requestLogin(obj.email,obj.phone);
        });

    }

    public LiveData<Resource<User>> getLoginLiveData(){
        return loginLiveData;
    }
    public void requestLogin(String email, String phone){
        loginMutableLiveData.postValue(new LoginParams(email, phone));
    }

    private class LoginParams{
        String email, phone;

        public LoginParams(String email, String phone) {
            this.email = email;
            this.phone = phone;
        }
    }

}
