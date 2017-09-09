package com.example.remember.newsapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.login.wedget.LoginActivity;
import com.example.remember.newsapp.login.wedget.RegisterActivity;

/**
 * Created by Administrator on 2017/9/8.
 */

public class RegisterDialog extends Dialog implements View.OnClickListener{
    private TextView tvContent;
    private String content;
    private TextView tvOk,tvCancel;
    private Activity context;
    private int type =1;

    public RegisterDialog(Activity mContext,int style){
        super(mContext, style);
    }

    public RegisterDialog(Activity  mContext, int style, int type,String content){
        super(mContext, style);
        this.context=mContext;
        this.content = content;
        this.type = type;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);
        tvContent = (TextView)findViewById(R.id.tv_dialog_content);
        tvOk = (TextView)findViewById(R.id.tv_dialog_ok);
        tvCancel = (TextView)findViewById(R.id.tv_dialog_cancel);
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        if (content != null){
            tvContent.setText(content);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_dialog_ok:
                dismiss();
                if (type==0){
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                    context.finish();
                }
                if (type ==1){
                    Intent intent = new Intent(context,RegisterActivity.class);
                    context.startActivity(intent);
                    context.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    context.finish();
                }
                break;
            case R.id.tv_dialog_cancel:
                dismiss();
                break;

        }
    }
}
