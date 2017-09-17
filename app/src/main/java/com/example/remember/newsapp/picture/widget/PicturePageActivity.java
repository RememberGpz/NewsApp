package com.example.remember.newsapp.picture.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.widget.HackyViewPager;

/**
 * Created by Remember on 2017/9/17.
 */

public class PicturePageActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_page);
        initToolbar();
        viewPager = (ViewPager)findViewById(R.id.hackyVP);

    }

    private void initToolbar(){
        View layout = findViewById(R.id.tb_picture);
        toolbar = (Toolbar)layout.findViewById(R.id.toolbar_picture);
        appBarLayout = (AppBarLayout)layout.findViewById(R.id.appBar_picture);
        toolbar.setNavigationIcon(R.drawable.ssdk_back_arr);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitle("NewsApp");

        toolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
    }
}
