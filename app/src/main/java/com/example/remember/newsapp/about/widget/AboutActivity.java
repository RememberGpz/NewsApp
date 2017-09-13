package com.example.remember.newsapp.about.widget;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.app.MyApplication;
import com.example.remember.newsapp.main.widget.MainActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

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

        MyApplication.getInstance().addAty(this);
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
                            sp.setTitle("NewsApp");
                            sp.setTitleUrl("https://github.com/RememberGpz/NewsApp/blob/master/README.md"); // 标题的超链接
                            sp.setText("一款Material Design风格的App");
                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                            sp.setSite("Remember_Gpz");
                            sp.setSiteUrl("https://www.github.com/Remember_Gpz/NewsApp/blob/master/README.md");
                            Platform qq = ShareSDK.getPlatform (QQ.NAME);
                            qq. setPlatformActionListener (AboutActivity.this); // 设置分享事件回调
                            // 执行图文分享
                            qq.share(sp);

//                            OnekeyShare oks = new OnekeyShare();
//
//                            oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
//                            oks.setTitleUrl("http://www.baidu.com");
//                            oks.setText("text");
//                            oks.setTitle("标题");
//
//                            oks.setPlatform(QQ.NAME);
//                            oks.show(AboutActivity.this);
                        }else if (item.get("ItemText").equals("Qzone")){
//                            Platform.ShareParams sp = new Platform.ShareParams();
//                            sp.setTitle("NewsApp");
//                            sp.setTitleUrl("https://www.github.com/RememberGpz"); // 标题的超链接
//                            sp.setText("Android开发...");
//                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
//
//// titleUrl是标题的网络链接，QQ和QQ空间等使用
//// text是分享文本，所有平台都需要这个字段
//                            Platform qq = ShareSDK.getPlatform (QZone.NAME);
//                            qq. setPlatformActionListener (AboutActivity.this); // 设置分享事件回调
//                            // 执行图文分享
//                            qq.share(sp);

                            Platform.ShareParams sp = new Platform.ShareParams();
                            sp.setTitle("NewsApp");
                            sp.setTitleUrl("https://github.com/RememberGpz/NewsApp/blob/master/README.md"); // 标题的超链接
                            sp.setText("一款Material Design风格的App");
                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                            sp.setSite("Remember_Gpz");
                            sp.setSiteUrl("https://www.github.com/Remember_Gpz/NewsApp/blob/master/README.md");

                            Platform qzone = ShareSDK.getPlatform (QZone.NAME);
// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
                            qzone.setPlatformActionListener (AboutActivity.this);
// 执行图文分享
                            qzone.share(sp);
                        }else if(item.get("ItemText").equals("WeChat")){
                            Platform.ShareParams sp = new Platform.ShareParams();
                            sp.setTitle("完成任务");
                            sp.setText("NewsApp");
                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                            sp.setShareType(Platform.SHARE_IMAGE);
                            Platform weChat = ShareSDK.getPlatform (Wechat.NAME);
                            weChat.setPlatformActionListener(AboutActivity.this);
                            weChat.share(sp);

                        }else if(item.get("ItemText").equals("Moments")) {
                            Platform.ShareParams sp = new Platform.ShareParams();
                            sp.setUrl("https://github.com/RememberGpz/NewsApp/blob/master/README.md");
                            sp.setTitle("完成任务");
                            sp.setText("NewsApp");
                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                            sp.setShareType(Platform.SHARE_WEBPAGE);
                            Platform moments = ShareSDK.getPlatform (WechatMoments.NAME);
                            moments.setPlatformActionListener(AboutActivity.this);
                            moments.share(sp);
                        }else if(item.get("ItemText").equals("Weibo")) {
                            Platform.ShareParams sp = new Platform.ShareParams();
//                            sp.setUrl("https://github.com/RememberGpz/NewsApp/blob/master/README.md");
//                            sp.setTitle("标题");
                            sp.setText("我的分享https://www.github.com/RememberGpz/NewsApp/blob/master/README.md");
                            sp.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");

                            Platform moments = ShareSDK.getPlatform (SinaWeibo.NAME);
                            moments.setPlatformActionListener(AboutActivity.this);
                            moments.share(sp);
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
