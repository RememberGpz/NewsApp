package com.example.remember.newsapp;

import android.graphics.Typeface;
import android.icu.text.DateFormat;

import org.litepal.LitePalApplication;

import java.lang.reflect.Field;

/**
 * Created by Remember on 2017/9/10.
 */

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/cr.otf");
        try {
                Field field = Typeface.class.getDeclaredField("MONOSPACE");
                field.setAccessible(true);
                field.set(null,typeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
