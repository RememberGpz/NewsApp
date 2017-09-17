package com.example.remember.newsapp.picture.net;

import com.example.remember.newsapp.beans.Picture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Remember on 2017/9/17.
 */

public interface GankAPI {

    @GET("data/{type}/{number}/{page}")
    Call<Picture> getGankData(@Path("type") String type, @Path("number") int  number, @Path("page") int page);
}
