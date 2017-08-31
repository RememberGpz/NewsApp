package com.example.remember.newsapp.main.presenter;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.main.view.MainView;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    public MainPresenterImpl(MainView mainView){
        this.mainView = mainView;
    }

    @Override
    public void switchItem(int id) {
        switch (id){
            case R.id.item_news:
                mainView.switchNews();
                break;
            case R.id.item_picture:
                mainView.switchPicture();
                break;
            case R.id.item_weather:
                mainView.switchWeather();
                break;
            case R.id.item_aboutme:
                mainView.switchAbout();
                break;
        }
    }
}
