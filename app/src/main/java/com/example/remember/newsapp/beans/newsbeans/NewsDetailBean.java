package com.example.remember.newsapp.beans.newsbeans;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/9/13.
 */

public class NewsDetailBean extends DataSupport {
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
