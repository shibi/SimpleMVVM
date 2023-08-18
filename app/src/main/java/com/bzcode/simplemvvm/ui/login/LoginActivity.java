package com.bzcode.simplemvvm.ui.login;

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
import com.bzcode.simplemvvm.data.remote.dto.login.User;
import com.bzcode.simplemvvm.data.repository.EmployeeRepository;
import com.bzcode.simplemvvm.domain.repository.IEmployeeRepository;
import com.bzcode.simplemvvm.domain.utils.AppDialogs;
import com.bzcode.simplemvvm.domain.utils.api_util.Resource;
import com.bzcode.simplemvvm.ui.dashboard.DashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private AppCompatEditText etEmail, etPhone;
    private AppCompatButton btn_login;
    private AppDialogs progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btn_login = findViewById(R.id.btn_signIn);

        progressDialog = new AppDialogs(this);


        //services
        ApiService apiService = ApiGenerator.createNoTokenApiService(ApiService.class);
        //repository
        IEmployeeRepository employeeRepository = new EmployeeRepository(apiService);
        LoginViewModelFactory factory = new LoginViewModelFactory(employeeRepository);
        //loginViewModel = new ViewModelProvider(this,factory).get(LoginViewModel.class);


        //set live data observer
        setObserver();

        //login button click
        btn_login.setOnClickListener(v -> onLoginButtonClick());
    }

    /**
     * observers
     * */
    private void setObserver(){
        loginViewModel.getLoginLiveData().observe(this, new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> userResource) {
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
                        showToast("LoginSuccess");
                        //redirect to dashboard
                        redirectTo(DashboardActivity.class, false);

                        break;
                    case ERROR:
                        //alert error to user
                        showToast(userResource.message);
                        break;
                }
            }
        });

    }



    /**
     * handle login button click
     * */
    private void onLoginButtonClick(){

        //request login
        loginViewModel.requestLogin(
                etEmail.getText().toString().trim(),
                etPhone.getText().toString().trim());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private <T> void redirectTo(Class<T> destination, boolean keepHistory){

        Intent destinationIntent = new Intent(this, destination);
        startActivity(destinationIntent);
        if(!keepHistory){
            finish();
        }

    }


}