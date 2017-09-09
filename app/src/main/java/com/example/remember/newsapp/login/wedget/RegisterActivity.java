package com.example.remember.newsapp.login.wedget;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.userbeans.User;
import com.example.remember.newsapp.utils.BtnCountTimer;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.RegisterDialog;
import com.example.remember.newsapp.utils.ToastUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by Administrator on 2017/9/5.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private MaterialEditText etPhoneNum,etPassword,etCode,etName;
    private TextView tvGetCode,tvRegister,tvAgreement,tvLoginHere;
    private String phoneNum,password,code,name;
    private LoadingDialog loadingDialog;
    private RegisterDialog dialog;
    private ImageView ivPsd;
    private boolean psdFlag;  //用于密码可视与不可视的开关变量

    private CollapsingToolbarLayout collapsingToolbarLayout;      //Materail Design标题栏设计风格
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this,"cfbf1ec371ed270f91166ff6c59391d9");
        loadingDialog = new LoadingDialog(this);
        dialog = new RegisterDialog(this,R.style.dialog_register,0,"Register Successful! Go To Login?");
        initView();
    }


    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.ctl_register);
        toolbar= (Toolbar)findViewById(R.id.tool_bar_register);
        tvLoginHere = (TextView)findViewById(R.id.tv_login_here) ;
        tvGetCode = (TextView)findViewById(R.id.tv_getcode);
        tvRegister =(TextView)findViewById(R.id.tv_rgt_register);
        tvAgreement = (TextView)findViewById(R.id.tv_agreement);
        etCode = (MaterialEditText)findViewById(R.id.et_rgt_code);
        etPhoneNum = (MaterialEditText)findViewById(R.id.et_rgt_phone);
        etPassword = (MaterialEditText)findViewById(R.id.et_rgt_password);
        etName = (MaterialEditText)findViewById(R.id.et_rgt_name);
        tvRegister.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        tvLoginHere.setOnClickListener(this);
//        ivPsd = (ImageView)findViewById(R.id.iv_psd);
//        ivPsd.setOnClickListener(this);

//        toolbar = (Toolbar)findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
//        TextView title = (TextView)toolbar.findViewById(R.id.tb_title);
//        title.setText("注    册");
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("");
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        psdFlag = false;

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("REGISTER");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_getcode:
                getCode();
                break;
            case R.id.tv_rgt_register:
                register();
                break;
//            case R.id.iv_psd:         //原来的版本有点击显示密码和隐藏密码
//                if (psdFlag == false){
//                    ivPsd.setImageResource(R.drawable.psd_vib);
//                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    etPassword.setSelection(etPassword.getText().length());
//                    psdFlag = true;
//                }else {
//                    ivPsd.setImageResource(R.drawable.psd_inv);
//                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    etPassword.setSelection(etPassword.getText().length());
//                    psdFlag =false;
//                }
//                break;
            case R.id.tv_agreement:
                Intent intent = new Intent(RegisterActivity.this,AgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_here:
                Intent intent1 = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent1);
                this.finish();
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

        phoneNum = etPhoneNum.getText().toString();
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(phoneNum)){
            Snackbar.make(tvGetCode,"Please Input Mobile！",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!isMobileNO(phoneNum)){
            Snackbar.make(tvGetCode,"Please Input Valid Mobile！",Snackbar.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();

        BmobQuery query = new BmobQuery("User");              //进行手机号码进行查询，判断该手机号码是否已经被注册
        query.addWhereEqualTo("phoneNum",phoneNum);
        query.order("createdAt");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {

                if (e == null){   //如果有数据则证明手机号码已经注册过了
                    if (jsonArray.length()>0){
                        loadingDialog.dissmiss();
                        Snackbar.make(tvGetCode,"This Mobile Has Registered！",Snackbar.LENGTH_SHORT).show();
                    }else if (jsonArray.length()<=0){
                        BmobSMS.requestSMSCode(phoneNum, "注册验证码", new QueryListener<Integer>() {  //如果没有数据则发送验证码
                            @Override
                            public void done(Integer integer, BmobException e) {
                                loadingDialog.dissmiss();
                                if (e == null){
                                    BtnCountTimer btnCountTimer = new BtnCountTimer(tvGetCode,60*1000,1000);
                                    btnCountTimer.start();
                                    Snackbar.make(tvGetCode,"The Code Was Send！",Snackbar.LENGTH_SHORT).show();

                                }else {

                                    Log.i("Register.Log",e.getMessage());
                                }
                            }
                        });
                    }
                }else {
                    Snackbar.make(tvGetCode,"Query Failed！",Snackbar.LENGTH_SHORT).show();
                    loadingDialog.dissmiss();
                }
            }
        });


    }

    private void register(){
        name = etName.getText().toString();
        phoneNum = etPhoneNum.getText().toString();
        password = etPassword.getText().toString();
        code = etCode.getText().toString();
        if (TextUtils.isEmpty(name)){
            Snackbar.make(tvGetCode,"Please Input UserName！",Snackbar.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(phoneNum)){
            Snackbar.make(tvGetCode,"Please Input Mobile",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Snackbar.make(tvGetCode,"Please Input Password",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)){
            Snackbar.make(tvGetCode,"Please Input Code",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!isMobileNO(phoneNum)){
            Snackbar.make(tvGetCode,"Please Input Valid Mobile",Snackbar.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();

        BmobQuery query = new BmobQuery("User");
        query.addWhereEqualTo("name",name);
        query.order("createdAt");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null){
                    if (jsonArray.length()>0) {
                        Snackbar.make(tvRegister, "The UserName Is Exist~", Snackbar.LENGTH_SHORT).show();
                        loadingDialog.dissmiss();
                    }
                    else if (jsonArray.length()==0){
                        BmobSMS.verifySmsCode(phoneNum, code, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    User user = new User();
                                    user.setName(name);
                                    user.setPassword(password);
                                    user.setPhoneNum(phoneNum);
                                    user.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            loadingDialog.dissmiss();
                                            if (e==null){
                                                dialog.show();
                                            }else {
                                                Snackbar.make(tvRegister,"Register Failed！",Snackbar.LENGTH_SHORT).show();
                                                Log.i("Register.Log",e.getMessage());
                                            }
                                        }
                                    });

                                }else {
                                    Snackbar.make(tvRegister,"The Code Is Error！",Snackbar.LENGTH_SHORT).show();
                                    loadingDialog.dissmiss();
                                    Log.i("Register.Log",e.getMessage());
                                }
                            }
                        });
                    }

                }else{
                    loadingDialog.dissmiss();
                    Snackbar.make(tvRegister,"Register Failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
                }

            }
        });



    }

    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
