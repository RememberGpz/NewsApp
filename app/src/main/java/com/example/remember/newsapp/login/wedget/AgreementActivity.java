package com.example.remember.newsapp.login.wedget;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.utils.LoadingDialog;

/**
 * Created by Remember on 2017/9/9.
 */

public class AgreementActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView wvAgmt;
    private LoadingDialog loadingDialog;
    private TextView loading,mtitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        loadingDialog = new LoadingDialog(this);
        wvAgmt = (WebView)findViewById(R.id.wv_agreement);
        toolbar = (Toolbar)findViewById(R.id.tb_agreement);
        loading = (TextView) findViewById(R.id.text_Loading);
        mtitle = (TextView) findViewById(R.id.title);

        TextView title = (TextView)findViewById(R.id.tb_title);
        title.setText("NewsApp协议");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadWebView();
    }

    private void loadWebView(){
        WebSettings webSettings = wvAgmt.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        wvAgmt.loadUrl("http://www.baidu.com");
        wvAgmt.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loadingDialog.dissmiss();
            }

        });
        //设置WebChromeClient类
        wvAgmt.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
                mtitle.setText(title);
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                }
            }
        });

    }
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvAgmt.canGoBack()) {
            wvAgmt.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview,避免内存泄漏
    @Override
    protected void onDestroy() {
        if (wvAgmt != null) {
            wvAgmt.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wvAgmt.clearHistory();

            ((ViewGroup) wvAgmt.getParent()).removeView(wvAgmt);
            wvAgmt.destroy();
            wvAgmt = null;
        }
        super.onDestroy();
    }


}
