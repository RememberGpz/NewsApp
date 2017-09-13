package com.example.remember.newsapp.login.wedget;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.remember.newsapp.Commons.ActivityTypes;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.app.MyApplication;
import com.example.remember.newsapp.main.widget.MainActivity;
import com.example.remember.newsapp.utils.BtnCountTimer;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.MdDialog;
import com.example.remember.newsapp.utils.UserInfoManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import static com.example.remember.newsapp.login.wedget.RegisterActivity.isMobileNO;

/**
 * Created by Administrator on 2017/9/8.
 */

public class CodeLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvGetCode,tvLogin;
    private MaterialEditText etPhone,etCode;
    private String phone,code;
    private Toolbar toolbar;
    private LoadingDialog loadingDialog;
//    private RegisterDialog dialog;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codelogin);
        MyApplication.getInstance().addAty(this);
        initView();
        toolbar = (Toolbar) findViewById(R.id.tool_bar_codelogin);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.ctl_codelogin);
        collapsingToolbarLayout.setTitle("Code Login");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initView(){
        loadingDialog = new LoadingDialog(this);
//        dialog = new RegisterDialog(this,R.style.dialog_register,1,"The Mobile Isn't Register\nAre You Want To Register Now?");
        tvGetCode = (TextView)findViewById(R.id.tv_lg_getcode);
        tvLogin = (TextView)findViewById(R.id.tv_codelg);
        tvGetCode.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        etPhone = (MaterialEditText)findViewById(R.id.et_lg_phone);
        etCode = (MaterialEditText)findViewById(R.id.et_lg_code);
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
            获取短信验证码的方法
        */
    private void getCode(){

        phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            Snackbar.make(tvGetCode,"Please Input Mobile！",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!isMobileNO(phone)){
            Snackbar.make(tvGetCode,"Please Input Valid Mobile！",Snackbar.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();

        BmobQuery query = new BmobQuery("User");              //进行手机号码进行查询，判断该手机号码是否已经被注册
        query.addWhereEqualTo("phoneNum",phone);
        query.order("createdAt");
        query.findObjects(CodeLoginActivity.this, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                //如果有数据则证明手机号码已经注册过了
                if (jsonArray.length()>0){
                    BmobSMS.requestSMSCode(CodeLoginActivity.this, phone, "注册验证码", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {//如果没有数据则发送验证码
                            loadingDialog.dissmiss();
                            if (e == null){
                                BtnCountTimer btnCountTimer = new BtnCountTimer(CodeLoginActivity.this,tvGetCode,60*1000,1000);
                                btnCountTimer.start();
                                Snackbar.make(tvGetCode,"The Code Is Sending！",Snackbar.LENGTH_SHORT).show();

                            }else {
                                Log.i("Register.Log",e.getMessage());
                            }
                        }
                    });
//                    BmobSMS.requestSMSCode(phone, "注册验证码", new QueryListener<Integer>() {  //如果没有数据则发送验证码
//                        @Override
//                        public void done(Integer integer, BmobException e) {
//                            loadingDialog.dissmiss();
//                            if (e == null){
//                                BtnCountTimer btnCountTimer = new BtnCountTimer(tvGetCode,60*1000,1000);
//                                btnCountTimer.start();
//                                Snackbar.make(tvGetCode,"The Code Is Sending！",Snackbar.LENGTH_SHORT).show();
//
//                            }else {
//                                Log.i("Register.Log",e.getMessage());
//                            }
//                        }
//                    });
                }else {
                    loadingDialog.dissmiss();
                    MdDialog.showDialog(CodeLoginActivity.this,"The Mobile Isn't Register,Are You Want To Register Now?","Tip:", ActivityTypes.CODELOGIN_ATY);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Snackbar.make(tvGetCode,"Query Failed！",Snackbar.LENGTH_SHORT).show();
                loadingDialog.dissmiss();
            }
        });
//        query.findObjectsByTable(new QueryListener<JSONArray>() {
//            @Override
//            public void done(JSONArray jsonArray, BmobException e) {
//
//                if (e == null){   //如果有数据则证明手机号码已经注册过了
//                    if (jsonArray.length()>0){
//                        BmobSMS.requestSMSCode(phone, "注册验证码", new QueryListener<Integer>() {  //如果没有数据则发送验证码
//                            @Override
//                            public void done(Integer integer, BmobException e) {
//                                loadingDialog.dissmiss();
//                                if (e == null){
//                                    BtnCountTimer btnCountTimer = new BtnCountTimer(tvGetCode,60*1000,1000);
//                                    btnCountTimer.start();
//                                    Snackbar.make(tvGetCode,"The Code Is Sending！",Snackbar.LENGTH_SHORT).show();
//
//                                }else {
//                                    Log.i("Register.Log",e.getMessage());
//                                }
//                            }
//                        });
//                    }else {
//                        loadingDialog.dissmiss();
//                        MdDialog.showDialog(CodeLoginActivity.this,"The Mobile Isn't Register,Are You Want To Register Now?","Tip:", ActivityTypes.CODELOGIN_ATY);
//                    }
//                }else {
//                    Snackbar.make(tvGetCode,"Query Failed！",Snackbar.LENGTH_SHORT).show();
//                    loadingDialog.dissmiss();
//                }
//            }
//        });


    }
    /*

     */
    private void login(){
        phone = etPhone.getText().toString().trim();
        code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            Snackbar.make(tvGetCode,"Please Input Mobile！",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)){
            Snackbar.make(tvGetCode,"Please Input The Code！",Snackbar.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();
        BmobSMS.verifySmsCode(CodeLoginActivity.this, phone, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    UserInfoManager.getManager().saveUserInfo(CodeLoginActivity.this,phone,"",1);     //1代表是手机号码登录
                    Intent intent = new Intent(CodeLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    CodeLoginActivity.this.finish();
                }else {

                    Snackbar.make(tvGetCode,"Login Failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
                    Log.i("CodeLoginActivity.Log",e.getMessage());
                }
            }
        });

//        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                loadingDialog.dissmiss();
//                if (e == null){
//                    UserInfoManager.getManager().saveUserInfo(CodeLoginActivity.this,phone,"",1);     //1代表是手机号码登录
//                    Intent intent = new Intent(CodeLoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    CodeLoginActivity.this.finish();
//                }else {
//
//                    Snackbar.make(tvGetCode,"Login Failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
//                    Log.i("CodeLoginActivity.Log",e.getMessage());
//                }
//            }
//        });
    }


}
