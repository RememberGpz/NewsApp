package com.example.remember.newsapp.utils;


import android.util.Log;

import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.beans.newsbeans.News;
import com.example.remember.newsapp.news.widget.NewsFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

/**
 * Created by Remember on 2017/9/1.
 */

public class Utility {
    public static boolean handleImageJson(String response){  //把请求回来的json数据解析成对象
        try {
            if (response!=null){
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject;
                Log.i("==",String.valueOf(jsonArray.length()));
                DataSupport.deleteAll(Picture.class);        //如果没有缓存条件语句的话，加载所有图片之前需要删除缓存，因为每次打开app加载的图片都会保存起来
                for (int i=0;i<jsonArray.length();i++){
                    Picture picture = new Picture();
                    jsonObject = jsonArray.getJSONObject(i);
                    picture.setUrl(jsonObject.getString("url"));
                    picture.setTitle(jsonObject.getString("title"));
                    picture.setHeight(jsonObject.getString("height"));
                    picture.setWidth(jsonObject.getString("width"));
                    picture.setSourceurl(jsonObject.getString("sourceurl"));
                    picture.setThumburl(jsonObject.getString("thumburl"));
                    picture.save();
                }
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean handleNews(String response,int type){
        try {
            String code = "";
            if (response!=null){
                switch (type){
                    case NewsFragment.HEAD:
                        code = Urls.TOP_ID;
                        break;
                    case NewsFragment.NBA:
                        code = Urls.NBA_ID;
                        break;
                    case NewsFragment.CAR:
                        code = Urls.CAR_ID;
                        break;
                    case NewsFragment.JOKE:
                        code = Urls.JOKE_ID;
                        break;
                    default:code = Urls.TOP_ID;
                }
                JSONObject jsonObjectT = new JSONObject(response);
                JSONArray jsonArray = jsonObjectT.getJSONArray(code);
                Log.i("utility--",jsonArray.getJSONObject(0).getString("title"));
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    News news1 = new News();
                    news1.setTitle(jsonObject.getString("title"));
                    news1.setImgsrc(jsonObject.getString("imgsrc"));
                    news1.setMtime(jsonObject.getString("mtime"));
                    news1.setSource(jsonObject.getString("source"));
                    news1.setType(type);
                    news1.save();
                }

                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return false;
    }


}
