package com.example.remember.newsapp.picture.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Remember on 2017/9/17.
 */

public class NewWord {
    private static GankAPI gankAPI;

    public static GankAPI getGankAPI(){
        if (gankAPI==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GankUrls.GANK_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gankAPI = retrofit.create(GankAPI.class);
            return gankAPI;
        }else
            return gankAPI;

    }

}
