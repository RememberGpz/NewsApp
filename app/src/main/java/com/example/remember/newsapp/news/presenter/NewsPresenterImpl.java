package com.example.remember.newsapp.news.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.beans.newsbeans.News;
import com.example.remember.newsapp.news.widget.NewsListFragment;
import com.example.remember.newsapp.utils.OkHttpUtil;
import com.example.remember.newsapp.utils.ToastUtil;
import com.example.remember.newsapp.utils.Utility;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remember on 2017/9/3.
 */

public class NewsPresenterImpl implements NewsPresenter {

    private NewsListFragment newsListFragment;

    public NewsPresenterImpl(NewsListFragment newsListFragment){
        this.newsListFragment = newsListFragment;
    }

    @Override
    public void loadNewsList() {
        String url = Urls.TOP_URL+Urls.TOP_ID+"/0-20.html";
        String url1 = "http://c.m.163.com/nc/article/headline/" + "T1348647909107" +"0-20.html";
        OkHttpUtil.getOkHttpUtil().sendHttpRequest( url, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                final String errorMsg = e.getMessage();
                newsListFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("请求失败---");
                        Log.i("NewsPresenterImpl-----",errorMsg);
                        newsListFragment.closeRefleshing();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("NewsPresenterImpl-----","news == null!!!!");
                News news = Utility.handleNews(response.body().string());
                if (news != null){
                    final List<News> newsList = new ArrayList<>();
                    newsList.add(news);
                    newsListFragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsListFragment.addNews(newsList);
                            newsListFragment.closeRefleshing();
                        }
                    });

                }else {
                    Log.i("NewsPresenterImpl-----","news == null!");
                }
            }
        });
    }
}
