package com.example.remember.newsapp.about.widget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.R;

/**
 * Created by Administrator on 2017/8/31.
 */

public class AboutActivity extends AppCompatActivity{
    private ImageView ivBackground;
    private CollapsingToolbarLayout ltl;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_aboutme);
        ivBackground  = (ImageView)findViewById(R.id.iv_aboutme_title);
        ltl = (CollapsingToolbarLayout)findViewById(R.id.collapsingTbL);
        toolbar = (Toolbar)findViewById(R.id.toolbar_aboutme);

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
}
