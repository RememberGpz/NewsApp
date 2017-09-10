package com.example.remember.newsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.remember.newsapp.beans.userbeans.UserInfo;

/**
 * Created by Remember on 2017/9/10.
 */

public class UserInfoManager {
    private static UserInfoManager manager;
    private UserInfoManager(){
    }
    public static UserInfoManager getManager(){
        if (manager == null){
            manager = new UserInfoManager();
        }
        return manager;
    }

    public void saveUserInfo(Context context,String userName, String password,int type){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password",password);
        editor.putString("userName",userName);
        editor.putInt("type",type);
        editor.apply();
    }


}
