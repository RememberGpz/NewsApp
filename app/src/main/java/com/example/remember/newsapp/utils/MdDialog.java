package com.example.remember.newsapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.remember.newsapp.Commons.ActivityTypes;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.login.wedget.CodeLoginActivity;
import com.example.remember.newsapp.login.wedget.LoginActivity;
import com.example.remember.newsapp.login.wedget.RegisterActivity;

/**
 * Created by Remember on 2017/9/10.
 */

public class MdDialog {
    public static void showDialog(final Context context, String content,String title,final int type){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setIcon(R.drawable.tips)
                .setMessage(content)
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (type){
                            case ActivityTypes.REGISTER_ATY:
                                ((RegisterActivity)context).onBackPressed();
                                break;
                            case ActivityTypes.CODELOGIN_ATY:
                                Intent intent1 = new Intent(context,RegisterActivity.class);
                                context.startActivity(intent1);
                                ((CodeLoginActivity)context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                ((CodeLoginActivity)context).finish();
                                break;
                        }
                    }
                })

                .show();
    }
}
