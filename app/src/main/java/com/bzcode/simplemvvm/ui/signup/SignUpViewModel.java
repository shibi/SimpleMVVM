package com.bzcode.simplemvvm.ui.signup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bzcode.simplemvvm.data.remote.dto.register.RegisterResponse;
import com.bzcode.simplemvvm.domain.repository.IEmployeeRepository;
import com.bzcode.simplemvvm.domain.utils.api_util.Resource;

/**
 * @author Shibin
 * created on 01-08-2023 at 12:05
 */
public class SignUpViewModel extends ViewModel {

    private final IEmployeeRepository repository;

    //Login
    private LiveData<Resource<RegisterResponse>> signupLiveData;
    private MutableLiveData<SignupParams> signupMutableLiveData = new MutableLiveData<>();

    public SignUpViewModel(IEmployeeRepository repository){
        this.repository = repository;

        //register
        signupLiveData = Transformations.switchMap(signupMutableLiveData, obj ->{
            if(obj==null){
                return new MutableLiveData<>();
            }
            return repository.registerUser(obj.name, obj.email,obj.phone);
        });

    }

    public LiveData<Resource<RegisterResponse>> getSignupLiveData(){
        return signupLiveData;
    }
    public void requestSignup(String name,String email, String phone){
        signupMutableLiveData.postValue(new SignupParams(name,email, phone));
    }

    private class SignupParams{
        String name, email, phone;
        public SignupParams(String name,String email, String phone) {
            this.email = email;
            this.phone = phone;
        }
    }


    static class SignUpViewModelFactory implements ViewModelProvider.Factory{

        private final IEmployeeRepository repository;

        public SignUpViewModelFactory(IEmployeeRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == SignUpViewModel.class) {
                return (T) new SignUpViewModel(repository);
            }
            return null;
        }
    }

}
