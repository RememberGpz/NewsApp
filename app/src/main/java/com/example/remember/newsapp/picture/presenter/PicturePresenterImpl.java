package com.example.remember.newsapp.picture.presenter;

import android.os.Handler;
import android.util.Log;

import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.picture.widget.PictureFragment;
import com.example.remember.newsapp.utils.OkHttpUtil;
import com.example.remember.newsapp.utils.Utility;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

/**
 * Created by Remember on 2017/9/1.
 * 使用okhttp时使用的类
 */

public class PicturePresenterImpl implements PicturePresenter{
    private PictureFragment pictureFragment;
    public PicturePresenterImpl(PictureFragment pictureFragment){
        this.pictureFragment = pictureFragment;
    }

    @Override
    public void loadImageList() {
        OkHttpUtil.getOkHttpUtil().sendHttpRequest(Urls.IMAGE_URL, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (Utility.handleImageJson(response.body().string())){
                    final List<Picture> pictures = DataSupport.findAll(Picture.class);
                    Log.i("presenterimpl==",String.valueOf(pictures.size()));
                    pictureFragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            pictureFragment.addPicture(pictures);
                        }
                    });
                }else {
                    Log.i("PicturePresenterImlp","数据解析错误！------------");
                }
            }
        });
    }
}
