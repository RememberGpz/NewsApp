package com.example.remember.newsapp.news.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.beans.newsbeans.News;
import com.example.remember.newsapp.news.widget.NewsFragment;
import com.example.remember.newsapp.news.widget.NewsListFragment;
import com.example.remember.newsapp.utils.OkHttpUtil;
import com.example.remember.newsapp.utils.ToastUtil;
import com.example.remember.newsapp.utils.Utility;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.net.URL;
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
    public void loadNewsList(final int type,int pagerIndex) {
        String url = getUrl(type,pagerIndex);
//        String url1 = "http://c.m.163.com/nc/article/headline/" + "T1348647909107" +"0-20.html";
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
//                News news = Utility.handleNews(response.body().string());

                if (Utility.handleNews(response.body().string(),type)){
                    final List<News>  newses = DataSupport.where("type=?",type+"").find(News.class);
                    newsListFragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsListFragment.addNews(newses);
                            newsListFragment.closeRefleshing();
                        }
                    });
                }else {
                    Log.i("NewsPresenterImpl-----","news == null!");
                }
            }
        });
    }

    private String getUrl(int type,int pagerIndex){
        StringBuffer url= new StringBuffer();
        switch (type){
            case NewsFragment.HEAD:
                url.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NBA:
                url.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.CAR:
                url.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.JOKE:
                url.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
        }
        return url.append(pagerIndex).append(20 + ".html").toString();
    }
}
