package com.example.remember.newsapp.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.login.wedget.LoginActivity;
import com.example.remember.newsapp.utils.UserInfoManager;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/9/4.
 */

public class SplashActivity extends AppCompatActivity {

    private ImageView  imageView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE); //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏

        View decorView = getWindow().getDecorView();    //隐藏导航栏
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_splash);

//        MyApplication.getInstance().addAty(this);

        initBmob();              //初始化bmobSDK

        imageView = (ImageView)findViewById(R.id.iv_splash);
        AlphaAnimation animation = new AlphaAnimation(0.4f,1.0f);
        animation.setDuration(2000);
        imageView.setAnimation(animation);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Glide.with(SplashActivity.this).load(R.drawable.splash4).into(imageView);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                skip();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void initBmob(){
        Bmob.initialize(this,"cfbf1ec371ed270f91166ff6c59391d9");
    }

    private void skip (){
        if (UserInfoManager.getManager().hasUserInfo(this)){
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
