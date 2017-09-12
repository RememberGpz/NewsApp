package com.example.remember.newsapp.news.widget;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.newsbeans.News;

/**
 * Created by Administrator on 2017/9/12.
 */

public class DetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout ctl;
    private Toolbar toolbar;
    private ImageView ivDetail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnews);
        Intent intent = getIntent();
        News news = (News)intent.getSerializableExtra("news");
        ctl = (CollapsingToolbarLayout)findViewById(R.id.ctl_detail);
        ivDetail = (ImageView)findViewById(R.id.iv_detail);


    }
}
