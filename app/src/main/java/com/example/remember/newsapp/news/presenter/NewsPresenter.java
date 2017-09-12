package com.example.remember.newsapp.news.presenter;

import com.example.remember.newsapp.beans.newsbeans.News;

import java.util.List;

/**
 * Created by Remember on 2017/9/3.
 */

public interface NewsPresenter {

    void loadNewsList(int type,int pagerIndex);
}
