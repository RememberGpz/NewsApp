package com.example.remember.newsapp.main.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.about.widget.AboutActivity;
import com.example.remember.newsapp.app.MyApplication;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.main.presenter.MainPresenterImpl;
import com.example.remember.newsapp.main.view.MainView;
import com.example.remember.newsapp.news.widget.NewsFragment;
import com.example.remember.newsapp.picture.widget.PictureFragment;
import com.example.remember.newsapp.services.AutoUpdateService;
import com.example.remember.newsapp.utils.LoadingDialog;
import com.example.remember.newsapp.utils.OkHttpUtil;
import com.example.remember.newsapp.utils.ToastUtil;
import com.example.remember.newsapp.utils.UserInfoManager;
import com.example.remember.newsapp.weather.widget.WeatherFragment;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindCallback;


public class MainActivity extends AppCompatActivity implements MainView{
    private Toolbar toolbar;
    private long currentTime ;
    private NavigationView nv;
    private MainPresenterImpl mainPresenterImpl;
    private DrawerLayout dl;
    private ImageView bing_pic;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences sp ;
    private LoadingDialog loadingDialog;
    private SharedPreferences userSP;
    private String userName;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addAty(this);

        loadingDialog = new LoadingDialog(this);

        currentTime = 0;
        userSP = getSharedPreferences("userInfo", Context.MODE_PRIVATE);          //获取保存用户信息的sp
        userName = userSP.getString("userName","");

        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);



        Intent intent = new Intent(this, AutoUpdateService.class);      //开启服务
        startService(intent);

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

        tvName = (TextView)view.findViewById(R.id.tv_name);
        setUserInfo();         //设置用户的用户名

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

    private void setUserInfo(){
        int type = userSP.getInt("type",0);
        if (type==0){
            if (!TextUtils.isEmpty(userName)){
                Log.i("MainActivity.Log","执行了type ==0");
                tvName.setText(userName);
            }else {
                Snackbar.make(tvName,"Data Error!",Snackbar.LENGTH_SHORT).show();
            }

        }else if(type == 1){     //如果是用手机号码登录，先判断sharedpreference里有没有用户名，没有就去后台加载
            Log.i("MainActivity.Log","zhixing type ==1");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BmobQuery query = new BmobQuery("User");
                    query.addWhereEqualTo("phoneNum",userName);
                    query.findObjects(MainActivity.this, new FindCallback() {
                        @Override
                        public void onSuccess(final JSONArray jsonArray) {
                            if (jsonArray.length()>0){
                                    Log.i("MainActivity.Log","jsonArray.length()>0");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    tvName.setText(jsonArray.getJSONObject(0).getString("name"));
                                                } catch (JSONException e1) {
                                                e1.printStackTrace();
                                                }
                                            }
                                        });

                                }else {      //没有数据则证明数据出错
                                    Snackbar.make(tvName,"Data Error！！",Snackbar.LENGTH_SHORT).show();
                                }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Snackbar.make(tvName,"Service Error!",Snackbar.LENGTH_SHORT).show();
                        }
                    });
//                    query.findObjectsByTable(new QueryListener<JSONArray>() {
//                        @Override
//                        public void done(final JSONArray jsonArray, BmobException e) {
//                            if (e == null){
//                                if (jsonArray.length()>0){
//                                    Log.i("MainActivity.Log","jsonArray.length()>0");
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                try {
//                                                    tvName.setText(jsonArray.getJSONObject(0).getString("name"));
//                                                } catch (JSONException e1) {
//                                                e1.printStackTrace();
//                                                }
//                                            }
//                                        });
//
//                                }else {      //没有数据则证明数据出错
//                                    Snackbar.make(tvName,"Data Error！！",Snackbar.LENGTH_SHORT).show();
//                                }
//                            }else {
//                                Snackbar.make(tvName,"Service Error!",Snackbar.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
            }).start();

        }else if(type ==2){
            if (!TextUtils.isEmpty(userName)){
                Log.i("MainActivity.Log","执行了type ==0");
                tvName.setText(userName);
            }else {
                Snackbar.make(tvName,"Data Error!",Snackbar.LENGTH_SHORT).show();
            }
        }

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
                MyApplication.getInstance().removeAllAty();
            }else {
                currentTime = System.currentTimeMillis();
                Snackbar.make(dl,"Press Again Exit App",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void switchNews() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NewsFragment()).commit();
        getSupportActionBar().setTitle("News");
    }

    @Override
    public void switchPicture() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new PictureFragment()).commit();
        getSupportActionBar().setTitle("Pictures");
    }

    @Override
    public void switchWeather() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new WeatherFragment()).commit();
        getSupportActionBar().setTitle("Weather");
    }

    @Override
    public void switchAbout() {
        startActivity(new Intent(MainActivity.this,AboutActivity.class));
    }

    @Override
    public void exit() {
        UserInfoManager.getManager().deleteSP(this);
        MyApplication.getInstance().removeAllAty();
    }

}
