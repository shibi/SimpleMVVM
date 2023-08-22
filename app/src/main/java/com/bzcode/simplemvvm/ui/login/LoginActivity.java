package com.bzcode.simplemvvm.ui.login;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bzcode.simplemvvm.R;
import com.bzcode.simplemvvm.data.remote.api.ApiGenerator;
import com.bzcode.simplemvvm.data.remote.api.ApiService;
import com.bzcode.simplemvvm.data.remote.dto.login.User;
import com.bzcode.simplemvvm.data.repository.EmployeeRepository;
import com.bzcode.simplemvvm.domain.repository.IEmployeeRepository;
import com.bzcode.simplemvvm.domain.utils.api_util.Resource;
import com.bzcode.simplemvvm.ui.base.BaseActivity;
import com.bzcode.simplemvvm.ui.dashboard.DashboardActivity;
import com.bzcode.simplemvvm.ui.signup.SignUpActivity;

public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;

    private AppCompatEditText etEmail, etPhone;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        AppCompatButton btn_login = findViewById(R.id.btn_signIn);
        AppCompatTextView tv_signup = findViewById(R.id.tv_signup);


        //api service
        ApiService apiService = ApiGenerator.createNoTokenApiService(ApiService.class);
        //repository
        IEmployeeRepository employeeRepository = new EmployeeRepository(apiService);
        //view model factory to pass parameter repository
        LoginViewModel.LoginViewModelFactory factory = new LoginViewModel.LoginViewModelFactory(employeeRepository);
        //init viewmodel with factory
        loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        //set live data observer
        setObserver();

        //login button click
        btn_login.setOnClickListener(v -> onLoginButtonClick());
        tv_signup.setOnClickListener(v-> redirectTo(SignUpActivity.class, true));

    }

    /**
     * observer changes in viewmodel data
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
                        //hide progress
                        progressDialog.hideProgressbar();
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

        boolean isValidationSuccess = validate();

        //proceed only if validation success
        if(isValidationSuccess) {
            //request login
            loginViewModel.requestLogin(
                    etEmail.getText().toString().trim(),
                    etPhone.getText().toString().trim());
        }
    }


    private boolean validate(){

        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();

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

    /**
     * to show error in field and focus
     * */
    private void showError(AppCompatEditText inputField, String errorMsg){
        inputField.setError(errorMsg);
        inputField.requestFocus();
    }


}