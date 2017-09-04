package com.example.remember.newsapp.beans.userbeans;

import cn.bmob.v3.BmobObject;

/**
 * Created by Remember on 2017/9/5.
 */

public class User extends BmobObject {
    private int id;
    private String name;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
