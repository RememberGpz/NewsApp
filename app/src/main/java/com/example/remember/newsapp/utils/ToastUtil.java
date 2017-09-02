package com.example.remember.newsapp.utils;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.litepal.LitePalApplication;

/**
 * Created by Remember on 2017/9/3.
 */

public class ToastUtil {

    public static void showToast(String content){
        Toast.makeText(LitePalApplication.getContext(),content,Toast.LENGTH_SHORT).show();
    }
}
