package com.example.remember.newsapp.app;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;

import com.mob.MobApplication;

import org.litepal.LitePalApplication;
import org.litepal.util.Const;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class MyApplication extends com.mob.MobApplication {
    private List<Activity> activityList  = new LinkedList<Activity>();
    private static MyApplication myApplication;

    public MyApplication(){
        super();
    }


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
        LitePalApplication.initialize(this);
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
