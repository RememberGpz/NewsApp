package com.example.remember.newsapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import com.example.remember.newsapp.R;


/**
 * Created by Administrator on 2017/9/5.
 */

public class LoadingDialog {
    private Dialog dialog;
    private Window window;
    public  LoadingDialog(Activity activity){
        initView(activity);
    }

    private void initView(Activity activity){
        dialog = new Dialog(activity, R.style.CustomDialog);
        dialog.setCanceledOnTouchOutside(true);
        window = dialog.getWindow();
        window.setContentView(R.layout.dialog_loading);
    }
    public void show(){
        if (dialog.isShowing()){
            return;
        }else {
            dialog.show();
        }
    }

    public void dissmiss(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
