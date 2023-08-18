package com.bzcode.simplemvvm.domain.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.bzcode.simplemvvm.R;


public class AppDialogs {

    private Context mContext;

    private Dialog progressDialog;

    /**
     * to prevent dialogbox showing multiple times
     * */
    private boolean isDialogBoxShowing = false;

    /**
     * constructor
     * */
    public AppDialogs(Context _context){
        mContext = _context;
    }

    /**
     * To show action success dialog
     * @param message
     * @param listener click listener for data passing to main screen
     * */
    public void showSuccessDialog(String message , View.OnClickListener listener){
        try {

            createSingleActionAlertDialogBox(message, false, "ok", listener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * For common alerts
     * single action
     * */
    public void showAlertDialog(String message , View.OnClickListener listener){
        try {

            createSingleActionAlertDialogBox(message, false, "ok", listener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * show toast message
     * */
    private void showToast(String message){
        try {

            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

        }catch (Exception e){ }
    }

    /**
     * to show progress bar
     * */
    public void showProgressBar(){
        try{

            //check whether dialog box showing
            if(isDialogBoxShowing)
            {
                showToast("Dialog already showing");
                return;
            }

            if(progressDialog == null) {

                //set the flag true
                isDialogBoxShowing = true;

                //init the dialog for the alert
                progressDialog = createSimpleDialog(mContext, R.layout.view_progressbar, false);

                //avoid closing dialog on clicking outside
                progressDialog.setCancelable(false);

                //get the dialog window
                Window window = progressDialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                //set the layout params for the dialog window
                window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

            }else {
                progressDialog.show();
                Log.e("------------","progess showing");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * to show progress bar
     * */
    public void hideProgressbar(){
        try{

            //set the flag false
            isDialogBoxShowing = false;

            if(progressDialog!=null){
                progressDialog.dismiss();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * to create dual action dialog box
     * @param message
     * @param cancellable is cancellable
     * @param buttonName positive button name
     * @param listener action listener
     * */
    private void createSingleActionAlertDialogBox(String message, boolean cancellable, String buttonName, View.OnClickListener listener){
        try{

            //check whether dialog box showing
            if(isDialogBoxShowing)
            {
                showToast("Dialog already showing");
                return;
            }

            //set the flag true
            isDialogBoxShowing = true;

            //init the dialog for the alert
            final Dialog dialog = createSimpleDialog(mContext, R.layout.dialog_single_action_alert, false);

            //avoid closing dialog on clicking outside
            dialog.setCancelable(cancellable);

            //init the submit  button
            final AppCompatButton action_button = dialog.findViewById(R.id.btn_action);
            final AppCompatTextView txt_message = dialog.findViewById(R.id.tv_message);

            txt_message.setText(message);
            action_button.setText(buttonName);

            //get the dialog window
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            //set the layout params for the dialog window
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            //add click listener on YES button
            action_button.setOnClickListener(v -> {
                try {

                    isDialogBoxShowing = false;
                    dialog.dismiss();
                    if(listener!=null) {
                        listener.onClick(v);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * simple dialog stub
     * */
    private static Dialog createSimpleDialog(Context context, int layout, boolean alignTop) {

        try {
            final Dialog dialog = new Dialog(context);

            /*----- Aligning the dialog in top -----*/
            if (alignTop) {
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.y = 25;
                wlp.gravity = Gravity.TOP;
                window.setAttributes(wlp);
            }

            dialog.setContentView(layout);
            dialog.show();

            return dialog;

        } catch (Exception ex) {

            throw ex;
        }

    }

}
