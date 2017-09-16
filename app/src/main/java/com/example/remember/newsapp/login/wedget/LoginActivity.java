package com.example.remember.newsapp.login.wedget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.app.MyApplication;
import com.example.remember.newsapp.main.widget.MainActivity;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.UserInfoManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Remember on 2017/9/4.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,PlatformActionListener{
    private Toolbar toolbar;
    private TextView login,register,tvCodeLogin;
    private MaterialEditText metName,metPassword;
    private String name,password;
    private LoadingDialog loadingDialog;
    private ImageView ivPsd,ivQQ,ivWeiBo,ivWeixin;
    private boolean psdFlag ;
    private int type = 0;  //用户判断用户是用用户名登录还是手机号码登录 0代表用户名 1代表手机号码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyApplication.getInstance().addAty(this);
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
        ivQQ = (ImageView)findViewById(R.id.iv_qq);
        ivWeiBo = (ImageView)findViewById(R.id.iv_weibo);
        ivWeixin= (ImageView)findViewById(R.id.iv_weixin);
        ivPsd.setOnClickListener(this);
        ivQQ.setOnClickListener(this);
        ivWeiBo.setOnClickListener(this);
        ivWeixin.setOnClickListener(this);
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
            case R.id.tv_code_login1:              //如果是点击了CodeLogin跳转到手机验证码登录

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
                break;
            case R.id.iv_qq:
                type = 2;
                otherLogin(QQ.NAME);
                break;

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
        query.findObjects(this, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                Log.i("111---", "共 " + jsonArray.length() + "条数据");
                if (jsonArray.length() > 0) {
                    loadingDialog.dissmiss();
                    //如果是登录所需要查询则进行如下逻辑 ，-》判断密码
                    type = 0;
                    isTruePassword(jsonArray, password, name1);
                }else{
                    queryPhone(name1,password);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                loadingDialog.dissmiss();
                Snackbar.make(tvCodeLogin,"Login failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
            }
        });
//        query.findObjectsByTable(new QueryListener<JSONArray>() {
//            @Override
//            public void done(JSONArray jsonArray, BmobException e) {
//            if (e == null){
//
//                    Log.i("111---", "  -------");
//                    Log.i("111---","共 " + jsonArray.length() +"条数据");
//                if (jsonArray.length()>0) {
//                    loadingDialog.dissmiss();
//                      //如果是登录所需要查询则进行如下逻辑 ，-》判断密码
//                    type=0;
//                    isTruePassword(jsonArray,password,name1);
//
//                }else {  //如果通过用户名没有查找到，则通过手机号码再查一遍
//                    queryPhone(name1,password);
//                }
//             }else {
//                loadingDialog.dissmiss();
//                Snackbar.make(tvCodeLogin,"Login failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
//                Log.i("Login.Log",e.getMessage());
//            }
//                }
//        });
    }

    private void queryPhone(final String phone, final String password){
        BmobQuery query = new BmobQuery("User");
        query.addWhereEqualTo("phoneNum",phone);
        query.findObjects(LoginActivity.this, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (jsonArray.length()>0){  //如果有数据则说明该手机号码有注册，继续判断密码是否正确
                    type =1;
                    isTruePassword(jsonArray,password,phone);

                }else {
                    loadingDialog.dissmiss();
                    Snackbar.make(tvCodeLogin,"User Doesn't Exist！",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                loadingDialog.dissmiss();
                Snackbar.make(tvCodeLogin,"Login failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
            }
        });
//        query.findObjectsByTable(new QueryListener<JSONArray>() {
//            @Override
//            public void done(JSONArray jsonArray, BmobException e) {
//                if (e == null){
//                    if (jsonArray.length()>0){  //如果有数据则说明该手机号码有注册，继续判断密码是否正确
//                        type =1;
//                        isTruePassword(jsonArray,password,phone);
//
//                    }else {
//                        loadingDialog.dissmiss();
//                        Snackbar.make(tvCodeLogin,"User Doesn't Exist！",Snackbar.LENGTH_SHORT).show();
//                    }
//                }else {
//                    loadingDialog.dissmiss();
//                    Snackbar.make(tvCodeLogin,"Login failed！Please Try Again",Snackbar.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void isTruePassword(JSONArray jsonArray,String password,String phone){
        loadingDialog.dissmiss();
        try {
            if (jsonArray.getJSONObject(0).getString("password").equals(password)) {   //如果密码相等则登录成功
                UserInfoManager.getManager().saveUserInfo(this,phone,password,type);
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


    private void otherLogin(String name){
        Platform platform = ShareSDK.getPlatform(name);
        platform.setPlatformActionListener(LoginActivity.this);
        platform.authorize();
        platform.showUser(null);
    }

    //第三方登录后的回调

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        PlatformDb db = platform.getDb();
        String name=db.getUserName();
        Log.i("Login.log",name);
        UserInfoManager.getManager().saveUserInfo(this,name,"",type);  //2代表第三方登录,只需要传userName
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Snackbar.make(tvCodeLogin,"Login Error！",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Snackbar.make(tvCodeLogin,"Cancel Login！",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        MyApplication.getInstance().removeAllAty();
        super.onBackPressed();
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
