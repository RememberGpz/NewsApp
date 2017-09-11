package com.example.remember.newsapp.about.widget;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.main.widget.MainActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener,PlatformActionListener {
    private ImageView ivBackground;
    private CollapsingToolbarLayout ltl;
    private Toolbar toolbar;
    private FloatingActionButton fab_share;
    private ShareDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_aboutme);
        ivBackground  = (ImageView)findViewById(R.id.iv_aboutme_title);
        ltl = (CollapsingToolbarLayout)findViewById(R.id.collapsingTbL);
        toolbar = (Toolbar)findViewById(R.id.toolbar_aboutme);
        fab_share = (FloatingActionButton)findViewById(R.id.fab_contact_us);
        fab_share.setOnClickListener(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Glide.with(this).load(R.drawable.aboutme_bg).into(ivBackground);
        ltl.setTitle("About Me");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_contact_us:
                dialog = new ShareDialog(this);
                dialog.setCancelButtonOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        HashMap<String, Object> item=(HashMap<String, Object>) adapterView.getItemAtPosition(i);
                        if(item.get("ItemText").equals("QQ")){
                            Platform.ShareParams sp = new Platform.ShareParams();
                            sp.setTitle("测试分享的标题");
                            sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("测试分享的文本");
                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");

                            Platform qq = ShareSDK.getPlatform (QQ.NAME);
                            qq. setPlatformActionListener (AboutActivity.this); // 设置分享事件回调
                            // 执行图文分享
                            qq.share(sp);
                        }else if (item.get("ItemText").equals("Qzone")){
                            Platform.ShareParams sp = new Platform.ShareParams();
                            sp.setTitle("NewsApp");
                            sp.setTitleUrl("https://www.github.com/RememberGpz"); // 标题的超链接
                            sp.setText("Android开发...");
                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");

// titleUrl是标题的网络链接，QQ和QQ空间等使用
// text是分享文本，所有平台都需要这个字段
                            Platform qq = ShareSDK.getPlatform (QZone.NAME);
                            qq. setPlatformActionListener (AboutActivity.this); // 设置分享事件回调
                            // 执行图文分享
                            qq.share(sp);
                        }
                        dialog.dismiss();
                    }
                });

        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(this,"Share Successful！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this,"Share Failed！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this,"Cancel Share！",Toast.LENGTH_SHORT).show();
    }
}
