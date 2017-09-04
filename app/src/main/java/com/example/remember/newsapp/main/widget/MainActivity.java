package com.example.remember.newsapp.main.widget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.about.widget.AboutActivity;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.main.presenter.MainPresenterImpl;
import com.example.remember.newsapp.main.view.MainView;
import com.example.remember.newsapp.news.widget.NewsFragment;
import com.example.remember.newsapp.picture.widget.PictureFragment;
import com.example.remember.newsapp.utils.OkHttpUtil;
import com.example.remember.newsapp.utils.ToastUtil;
import com.example.remember.newsapp.weather.widget.WeatherFragment;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity implements MainView{
    private Toolbar toolbar;
    private long currentTime ;
    private NavigationView nv;
    private MainPresenterImpl mainPresenterImpl;
    private DrawerLayout dl;
    private ImageView bing_pic;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTime = 0;
        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        mainPresenterImpl= new MainPresenterImpl(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NewsApp");
        dl = (DrawerLayout)findViewById(R.id.dl);
        toggle = new ActionBarDrawerToggle(this,dl,toolbar,R.string.drawer_open,R.string.drawer_close);
        toggle.syncState();
        dl.setDrawerListener(toggle);



        nv = (NavigationView)findViewById(R.id.nv);
        View view = nv.getHeaderView(0);
        bing_pic = (ImageView)view.findViewById(R.id.iv_nv_bg);
        String bingpic = sp.getString("pic",null);
        if (bingpic!=null){
            Glide.with(this).load(bingpic).placeholder(R.color.colorPrimary).error(R.color.colorPrimary).into(bing_pic);
        }else {
            loadBingPic();
        }
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mainPresenterImpl.switchItem(item.getItemId());
                item.setChecked(true);
                dl.closeDrawers();
                return true;
            }
        });
        toolbar.setNavigationIcon(R.drawable.drawer2);
        switchNews();
    }


    private void loadBingPic(){
        OkHttpUtil.getOkHttpUtil().sendHttpRequest(Urls.BING_PIC, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("请求必应图片失败");
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response!=null){
                    final String bing_pic1 = response.body().string();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("pic",bing_pic1);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MainActivity.this).load(bing_pic1).placeholder(R.color.colorPrimary).error(R.color.colorPrimary).into(bing_pic);

                        }
                    });
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)){
            dl.closeDrawers();
        }else {
            if (System.currentTimeMillis()-currentTime<2000){
                List<Picture> list = DataSupport.findAll(Picture.class);
                if (list.size()>0){
                    Log.i("MainActivity==",String.valueOf(list.size()));
                    list.clear();
                    Log.i("MainActivity==",String.valueOf(list.size()));
                }

                this.finish();
                System.exit(0);
            }else {
                currentTime = System.currentTimeMillis();
                Toast.makeText(MainActivity.this,"再按一次退出应用",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void switchNews() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NewsFragment()).commit();
        getSupportActionBar().setTitle("新闻");
    }

    @Override
    public void switchPicture() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new PictureFragment()).commit();
        getSupportActionBar().setTitle("图片");
    }

    @Override
    public void switchWeather() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new WeatherFragment()).commit();
        getSupportActionBar().setTitle("天气");
    }

    @Override
    public void switchAbout() {
        startActivity(new Intent(MainActivity.this,AboutActivity.class));
    }

    @Override
    public void exit() {
        this.finish();
        System.exit(0);
    }
}
