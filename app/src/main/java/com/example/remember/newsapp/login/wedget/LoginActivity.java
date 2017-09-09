package com.example.remember.newsapp.login.wedget;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.newsbeans.ImageSrcBean;
import com.example.remember.newsapp.beans.userbeans.User;
import com.example.remember.newsapp.main.widget.MainActivity;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

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
    private TextView login,register,tvCodeLogin;
    private MaterialEditText metName,metPassword;
    private String name,password;
    private LoadingDialog loadingDialog;
    private ImageView ivPsd;
    private boolean psdFlag ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingDialog = new LoadingDialog(this);
        initView();
    }

    private void initView(){

        login = (TextView)findViewById(R.id.tv_login1);
        register = (TextView)findViewById(R.id.tv_register1);
        tvCodeLogin = (TextView)findViewById(R.id.tv_code_login1);
        tvCodeLogin.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        metName = (MaterialEditText) findViewById(R.id.met_login_name);
        metPassword = (MaterialEditText) findViewById(R.id.met_login_password);
        ivPsd = (ImageView)findViewById(R.id.iv_psd);
        ivPsd.setOnClickListener(this);
        psdFlag = false;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login1:
                login();
                break;
            case R.id.tv_register1:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.tv_code_login1:
                Intent intent1 = new Intent(LoginActivity.this,CodeLoginActivity.class);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.iv_psd:      //判断是否显示密码的逻辑操作
                if (psdFlag==false){
                    ivPsd.setImageResource(R.drawable.psd_vib);
                    metPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //显示密码
                    psdFlag=true;
                    metPassword.setSelection(metPassword.getText().length());                          //把光标移至文本最后
                }else {
                    ivPsd.setImageResource(R.drawable.psd_inv);
                    psdFlag = false;
                    metPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());  //隐藏密码
                    metPassword.setSelection(metPassword.getText().length());                          //把光标移至文本最后
                }

        }
    }
    private void login(){
        name = metName.getText().toString();
        password = metPassword.getText().toString();
        if (TextUtils.isEmpty(name)){
            Snackbar.make(tvCodeLogin,"Please Input Mobile OR UserName",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (name.length()>15){
            Snackbar.make(tvCodeLogin,"UserName OR Mobile Must Less Than 16",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Snackbar.make(tvCodeLogin,"Please Input Password!",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (password.length()<6||password.length()>15){
            Snackbar.make(tvCodeLogin,"Password Must Greater Than 6 And Less Than 16",Snackbar.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();
        quetyUser(name,password);

    }

    private void quetyUser(final String name1, final String password){
        final BmobQuery query = new BmobQuery("User");
        query.addWhereEqualTo("name",name1);
        query.order("createdAt");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
            if (e == null){

                    Log.i("111---", "  -------");
                    Log.i("111---","共 " + jsonArray.length() +"条数据");
                if (jsonArray.length()>0) {
                    loadingDialog.dissmiss();
                      //如果是登录所需要查询则进行如下逻辑 ，-》判断密码
                    isTruePassword(jsonArray,password);
                }else {  //如果通过用户名没有查找到，则通过手机号码再查一遍
                    queryPhone(name1,password);
                }
             }else {
                loadingDialog.dissmiss();
                Snackbar.make(tvCodeLogin,"Login failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
                Log.i("Login.Log",e.getMessage());
            }
                }
        });
    }

    private void queryPhone(final String phone, final String password){
        BmobQuery query = new BmobQuery("User");
        query.addWhereEqualTo("phoneNum",phone);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null){
                    if (jsonArray.length()>0){  //如果有数据则说明该手机号码有注册，继续判断密码是否正确
                        isTruePassword(jsonArray,password);
                    }else {
                        loadingDialog.dissmiss();
                        Snackbar.make(tvCodeLogin,"User Doesn't Exist！",Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    loadingDialog.dissmiss();
                    Snackbar.make(tvCodeLogin,"Login failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void isTruePassword(JSONArray jsonArray,String password){
        loadingDialog.dissmiss();
        try {
            if (jsonArray.getJSONObject(0).getString("password").equals(password)) {   //如果密码相等则登录成功
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                return;
            } else {
                Snackbar.make(tvCodeLogin,"Password Error！",Snackbar.LENGTH_SHORT).show();
                return;
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }
}
