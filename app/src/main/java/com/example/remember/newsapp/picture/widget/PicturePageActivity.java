package com.example.remember.newsapp.picture.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.beans.newsbeans.ImageSrcBean;
import com.example.remember.newsapp.widget.HackyViewPager;

import org.litepal.crud.DataSupport;

/**
 * Created by Remember on 2017/9/17.
 */

public class PicturePageActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ImageView ivDetail;
    private  int index;
    private static final String EXTRA_INDEX="extra_index";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_page);
        initToolbar();
        ivDetail =(ImageView)findViewById(R.id.iv_picture_detail);
        Intent intent = getIntent();
        index = intent.getIntExtra(EXTRA_INDEX,-1);
    }

    @Override
    protected void onResume() {
        String url = DataSupport.find(Picture.class,0).getResults().get(index).getUrl();
        Glide.with(this).load(url).placeholder(R.drawable.default_picture).error(R.drawable.load_error).into(ivDetail);
        super.onResume();
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


    public static Intent launch(Activity activity,int index){
        Intent intent = new Intent(activity,PicturePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_INDEX,index);
        return intent;
    }
}
