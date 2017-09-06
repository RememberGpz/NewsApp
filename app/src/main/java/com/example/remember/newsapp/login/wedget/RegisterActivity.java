package com.example.remember.newsapp.login.wedget;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.utils.ToastUtil;

import cn.bmob.v3.BmobSMS;

/**
 * Created by Administrator on 2017/9/5.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etPhoneNum,etPassword,etCode;
    private TextView tvGetCode,tvRegister;
    private Toolbar toolbar;
    private String phoneNum,password,code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView(){
        tvGetCode = (TextView)findViewById(R.id.tv_getcode);
        tvRegister =(TextView)findViewById(R.id.tv_rgt_register);
        etCode = (EditText)findViewById(R.id.et_rgt_code);
        etPhoneNum = (EditText)findViewById(R.id.et_rgt_phone);
        etPassword = (EditText)findViewById(R.id.et_rgt_password);
        tvRegister.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        toolbar = (Toolbar)findViewById(R.id.tb_register);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("注册");
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.tv_getcode:
                getCode();
                break;
            case R.id.tv_rgt_register:
                register();
                break;
        }
    }

    private void getCode(){
        phoneNum = etPhoneNum.getText().toString();
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(phoneNum)){
            ToastUtil.showToast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast("请输入密码");
            return;
        }


    }

    private void register(){

    }
}
