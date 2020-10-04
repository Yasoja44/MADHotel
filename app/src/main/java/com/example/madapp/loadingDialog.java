package com.example.madapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class loadingDialog {

 private    Activity activity;
 private    AlertDialog dialog;

    loadingDialog(Activity activity1){
        activity=activity1;
    }

    void startLoadingDailog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_loading,null));
        builder.setCancelable(false);

        dialog =builder.create();
        dialog.show();
    }

    void dismissDailog(){
        dialog.dismiss();
    }
}
