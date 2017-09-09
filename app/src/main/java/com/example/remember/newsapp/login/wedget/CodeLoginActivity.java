package com.example.remember.newsapp.login.wedget;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.main.widget.MainActivity;
import com.example.remember.newsapp.utils.BtnCountTimer;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.RegisterDialog;
import com.example.remember.newsapp.utils.ToastUtil;

import org.json.JSONArray;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.remember.newsapp.login.wedget.RegisterActivity.isMobileNO;

/**
 * Created by Administrator on 2017/9/8.
 */

public class CodeLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvGetCode,tvLogin;
    private EditText etPhone,etCode;
    private String phone,code;
    private Toolbar toolbar;
    private LoadingDialog loadingDialog;
    private RegisterDialog dialog;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codelogin);
        initView();
        toolbar = (Toolbar)findViewById(R.id.tb_codelogin);
        toolbar.setNavigationIcon(R.drawable.back);
        TextView title = toolbar.findViewById(R.id.tb_title);
        title.setText("动 态 登 录");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        actionBar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void initView(){
        loadingDialog = new LoadingDialog(this);
        dialog = new RegisterDialog(this,R.style.dialog_register,1,"该手机还没有注册，现在\n去注册吧");
        tvGetCode = (TextView)findViewById(R.id.tv_lg_getcode);
        tvLogin = (TextView)findViewById(R.id.tv_codelg);
        tvGetCode.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        etPhone = (EditText)findViewById(R.id.et_lg_phone);
        etCode = (EditText)findViewById(R.id.et_lg_code);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_lg_getcode:
                getCode();
                break;
            case R.id.tv_codelg:

                login();
                break;
        }
    }

    /*
        获取短信验证码的方法
    */
    private void getCode(){

        phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            ToastUtil.showToast("请输入手机号码");
            return;
        }
        if (!isMobileNO(phone)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }
        loadingDialog.show();

        BmobQuery query = new BmobQuery("User");              //进行手机号码进行查询，判断该手机号码是否已经被注册
        query.addWhereEqualTo("phoneNum",phone);
        query.order("createdAt");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {

                if (e == null){   //如果有数据则证明手机号码已经注册过了
                    if (jsonArray.length()>0){
                        BmobSMS.requestSMSCode(phone, "注册验证码", new QueryListener<Integer>() {  //如果没有数据则发送验证码
                            @Override
                            public void done(Integer integer, BmobException e) {
                                loadingDialog.dissmiss();
                                if (e == null){
                                    BtnCountTimer btnCountTimer = new BtnCountTimer(tvGetCode,60*1000,1000);
                                    btnCountTimer.start();
                                    ToastUtil.showToast("验证码已发送");

                                }else {
                                    Log.i("Register.Log",e.getMessage());
                                }
                            }
                        });
                    }else {
                        loadingDialog.dissmiss();
                        dialog.show();
                    }
                }else {
                    ToastUtil.showToast("查询失败"+e.getMessage());
                    loadingDialog.dissmiss();
                }
            }
        });


    }
    /*

     */
    private void login(){
        phone = etPhone.getText().toString().trim();
        code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            ToastUtil.showToast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtil.showToast("请输入验证码");
            return;
        }
        loadingDialog.show();
        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                loadingDialog.dissmiss();
                if (e == null){
                    Intent intent = new Intent(CodeLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    CodeLoginActivity.this.finish();
                }else {

                    Snackbar.make(tvGetCode,"登录失败！",Snackbar.LENGTH_SHORT).show();
                    Log.i("CodeLoginActivity.Log",e.getMessage());
                }
            }
        });
    }
}
