package com.example.remember.newsapp.login.wedget;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.userbeans.User;
import com.example.remember.newsapp.main.widget.MainActivity;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Remember on 2017/9/4.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private TextView login,register;
    private EditText etName,etPassword;
    private String name,password;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingDialog = new LoadingDialog(this);
        initView();
    }

    private void initView(){
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("登录");

        login = (TextView)findViewById(R.id.tv_login);
        register = (TextView)findViewById(R.id.tv_register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        etName = (EditText)findViewById(R.id.et_login_name);
        etPassword = (EditText)findViewById(R.id.et_login_password);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void login(){
        name = etName.getText().toString();
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast("请输入密码");
            return;
        }
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                quetyUser(name,password,0);
            }
        }).start();

    }

    private void quetyUser(final String name1, final String password, final int type){
        BmobQuery query = new BmobQuery("User");
        query.addWhereEqualTo("name",name1);
        query.order("createdAt");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null){
                    loadingDialog.dissmiss();
                        Log.i("111---", "  -------");
                        Log.i("111---","共 " + jsonArray.length() +"条数据");
                    if (jsonArray.length()>0) {
                        if (type == 1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast("该用户已存在了哦！");

                                }
                            });

                            return;
                        }

                        if (type == 0) {      //如果是登录所需要查询则进行如下逻辑 ，-》判断密码
                            try {
                                if (jsonArray.getJSONObject(0).getString("password").equals(password)) {   //如果密码相等则登录成功
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    return;
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.showToast("密码错误！");
                                        }
                                    });

                                    return;
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }else {

                        if (type == 1) {            //如果是注册所需要的查询则如下
                            User user = new User();
                            user.setName(name);
                            user.setPassword(password);
                            user.save(new SaveListener<String>() {
                                @Override
                                public void done(final String s,final BmobException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingDialog.dissmiss();
                                            if (e == null) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ToastUtil.showToast("注册成功--" + s);
                                                    }
                                                });

                                            } else {
                                               runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       ToastUtil.showToast("注册失败" + e.getMessage());
                                                   }
                                               });
                                            }
                                        }
                                    });

                                }
                            });
                        } else if (type == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast("没用该用户！");
                                }
                            });

                        }
                    }
                 }
                }
        });
    }

    private void register(){
        name = etName.getText().toString();
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast("请输入密码");
            return;
        }
        Log.i("name",name);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                quetyUser(name,password,1);
            }
        }).start();


//        User user = new User();
//        user.setName(name);
//        user.setPassword(password);
//        user.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null){
//                    ToastUtil.showToast("注册成功--" + s);
//                }else {
//                    ToastUtil.showToast("注册失败"+e.getMessage());
//                }
//            }
//        });
    }
}
