package com.example.remember.newsapp.utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Remember on 2017/9/1.
 */

public class OkHttpUtil {
    public static OkHttpUtil okHttpUtil;
    private OkHttpClient okHttpClient;

    private OkHttpUtil(){                               //单例模式
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
    }

    public static OkHttpUtil getOkHttpUtil(){
        if (okHttpUtil==null){
            okHttpUtil = new OkHttpUtil();
        }
        return okHttpUtil;
    }

    public void sendHttpRequest(String url, Callback callback){           //发送网络请求
        okHttpClient.newCall(new Request.Builder().url(url).build()).enqueue(callback);
    }
}
