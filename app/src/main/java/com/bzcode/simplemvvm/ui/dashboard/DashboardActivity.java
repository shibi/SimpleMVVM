package com.bzcode.simplemvvm.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.bzcode.simplemvvm.R;
import com.bzcode.simplemvvm.ui.login.LoginActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button btnLogout = findViewById(R.id.btn_logout);


        btnLogout.setOnClickListener(v-> redirectTo(LoginActivity.class, false));

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