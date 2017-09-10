package com.example.remember.newsapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.icu.text.DateFormat;

import org.litepal.LitePalApplication;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Remember on 2017/9/10.
 */

public class MyApplication extends LitePalApplication {
    public List<Activity> activityList = new LinkedList<>();
    public static MyApplication myApplication;


    public  static MyApplication getInstance(){
        if (myApplication==null){
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    public void addAty(Activity activity){
        if (activity!=null){
            activityList.add(activity);
        }
    }

    public void removeAllAty(){
        if (activityList.size()>0){
            for (Activity aty : activityList){
                if (aty!=null){
                    aty.finish();
                }
            }
        }
    }

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
