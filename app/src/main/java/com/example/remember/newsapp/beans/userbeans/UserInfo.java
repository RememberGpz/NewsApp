package com.example.remember.newsapp.beans.userbeans;

/**
 * Created by Remember on 2017/9/10.
 */

public class UserInfo {
    private int type;   //
    private String userName;
    private String password;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
