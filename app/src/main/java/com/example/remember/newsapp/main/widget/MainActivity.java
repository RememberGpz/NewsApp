package com.example.remember.newsapp.main.widget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.about.widget.AboutActivity;
import com.example.remember.newsapp.main.presenter.MainPresenterImpl;
import com.example.remember.newsapp.main.view.MainView;
import com.example.remember.newsapp.news.widget.NewsFragment;
import com.example.remember.newsapp.picture.widget.PictureFragment;
import com.example.remember.newsapp.weather.widget.WeatherFragment;


public class MainActivity extends AppCompatActivity implements MainView{
    private Toolbar toolbar;
    private long currentTime ;
    private NavigationView nv;
    private MainPresenterImpl mainPresenterImpl;
    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTime = 0;
        mainPresenterImpl= new MainPresenterImpl(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NewsApp");
        dl = (DrawerLayout)findViewById(R.id.dl);
        toggle = new ActionBarDrawerToggle(this,dl,toolbar,R.string.drawer_open,R.string.drawer_close);
        toggle.syncState();
        dl.setDrawerListener(toggle);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mainPresenterImpl.switchItem(item.getItemId());
                item.setChecked(true);
                dl.closeDrawers();
                return true;
            }
        });
        toolbar.setNavigationIcon(R.drawable.menu);
        switchNews();
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
}
