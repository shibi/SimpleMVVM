package com.bzcode.simplemvvm.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bzcode.simplemvvm.domain.utils.AppDialogs;

/**
 * @author shibin
 * Created 22-08-2023 at 16:43
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected AppDialogs progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layout = setContentLayout();
        setContentView(layout);

        progressDialog = new AppDialogs(this);

        initView();
    }

    protected abstract int setContentLayout();
    protected abstract void initView();


    //to show toast
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void loge(String msg){
        Log.e("------------",msg);
    }
    //to redirect to next screen
    protected <T> void redirectTo(Class<T> destination, boolean keepHistory){

        Intent destinationIntent = new Intent(this, destination);
        startActivity(destinationIntent);
        if(!keepHistory){
            finish();
        }
    }
}
