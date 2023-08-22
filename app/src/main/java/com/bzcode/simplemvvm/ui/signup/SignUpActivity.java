package com.bzcode.simplemvvm.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bzcode.simplemvvm.R;
import com.bzcode.simplemvvm.data.remote.api.ApiGenerator;
import com.bzcode.simplemvvm.data.remote.api.ApiService;
import com.bzcode.simplemvvm.data.remote.dto.register.RegisterResponse;
import com.bzcode.simplemvvm.data.repository.EmployeeRepository;
import com.bzcode.simplemvvm.domain.repository.IEmployeeRepository;
import com.bzcode.simplemvvm.domain.utils.AppDialogs;
import com.bzcode.simplemvvm.domain.utils.api_util.Resource;
import com.bzcode.simplemvvm.ui.login.LoginActivity;


public class SignUpActivity extends AppCompatActivity {

    private AppDialogs progressDialog;
    private SignUpViewModel signUpViewModel;

    private AppCompatEditText etName, etEmail, etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new AppDialogs(this);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        AppCompatButton btn_submit = findViewById(R.id.btn_submit);

        //api service
        ApiService apiService = ApiGenerator.createNoTokenApiService(ApiService.class);
        //repository
        IEmployeeRepository employeeRepository = new EmployeeRepository(apiService);
        //view model factory to pass parameter repository
        SignUpViewModel.SignUpViewModelFactory factory = new SignUpViewModel.SignUpViewModelFactory(employeeRepository);
        //init viewmodel
        signUpViewModel = new ViewModelProvider(this, factory).get(SignUpViewModel.class);

        //observe data changes
        setObservers();

        btn_submit.setOnClickListener(v-> onSubmitClick());

    }

    private void setObservers(){

        signUpViewModel.getSignupLiveData().observe(this, new Observer<Resource<RegisterResponse>>() {
            @Override
            public void onChanged(Resource<RegisterResponse> userResource) {
                switch (userResource.status)
                {
                    case LOADING:
                        //show progress
                        progressDialog.showProgressBar();

                        break;
                    case SUCCESS:
                        //hide progress
                        progressDialog.hideProgressbar();
                        //alert user
                        showToast("Registration Success");
                        //redirect to dashboard
                        redirectTo(LoginActivity.class, false);

                        break;
                    case ERROR:
                        //hide progress
                        progressDialog.hideProgressbar();
                        //alert error to user
                        showToast(userResource.message);
                        break;
                }
            }
        });

    }

    private void onSubmitClick(){

        boolean isValidationSuccess = validate();

        //proceed only if validation success
        if(isValidationSuccess) {
            //request signup
            signUpViewModel.requestSignup(
                    etName.getText().toString(),
                    etEmail.getText().toString(),
                    etEmail.getText().toString()
            );
        }

    }

    private boolean validate(){
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();

        if(name.isEmpty()){
            showError(etName, "Enter name");
            return false;
        }

        if(email.isEmpty()){
            showError(etEmail, "Enter email");
            return false;
        }

        if(phone.isEmpty()){
            showError(etPhone, "Enter phone");
            return false;
        }

        //More checking here...

        return true;
    }

    private void showError(AppCompatEditText inputField, String errorMsg){
        inputField.setError(errorMsg);
        inputField.requestFocus();
    }

    /**
     * to show toast
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * to redirect to next screen
     * */
    private <T> void redirectTo(Class<T> destination, boolean keepHistory){

        Intent destinationIntent = new Intent(this, destination);
        startActivity(destinationIntent);
        if(!keepHistory){
            finish();
        }
    }

}