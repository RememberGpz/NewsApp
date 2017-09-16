package com.example.remember.newsapp.news.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.app.MyApplication;
import com.example.remember.newsapp.beans.newsbeans.News;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.OkHttpUtil;
import com.example.remember.newsapp.utils.Utility;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2017/9/12.
 */

public class DetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout ctl;
    private Toolbar toolbar;
    private ImageView ivDetail;
    private WebView wvDetail;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnews);
        MyApplication.getInstance().addAty(this);
        loadingDialog = new LoadingDialog(this);

        Intent intent = getIntent();
        ctl = (CollapsingToolbarLayout)findViewById(R.id.ctl_detail);
        ivDetail = (ImageView)findViewById(R.id.iv_detail);
        wvDetail = (WebView)findViewById(R.id.wv_news_detail);
        ctl.setTitle(intent.getStringExtra("title"));
        toolbar = (Toolbar)findViewById(R.id.tb_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Glide.with(this).load(intent.getStringExtra("imgsrc")).placeholder(R.drawable.default_picture).error(R.drawable.load_error).into(ivDetail);
        WebSettings webSettings = wvDetail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        showContent(intent.getStringExtra("docid"));       //网络请求,然后把解析出来的数据显示在webview
    }

    private void showContent(final String docid){
        loadingDialog.show();
        OkHttpUtil.getOkHttpUtil().sendHttpRequest(Urls.HOST + "nc/article/"+docid + "/full.html", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Snackbar.make(wvDetail,"Request Failed!",Snackbar.LENGTH_SHORT).show();
                loadingDialog.dissmiss();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String content = Utility.handleDetailNews(response.body().string(),docid);
                if (!TextUtils.isEmpty(content)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dissmiss();
                            wvDetail.loadDataWithBaseURL(null,content,"text/html","GBK",null);
                        }
                    });
                }else {
                    loadingDialog.dissmiss();
                    Snackbar.make(wvDetail, "Data Is Null!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
