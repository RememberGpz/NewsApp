package com.example.remember.newsapp.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.remember.newsapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class NewsFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private TabLayout tabLayout ;
    private ViewPager viewPager;

    public static final int HEAD = 1;
    public static final int NBA = 2;
    public static final int CAR = 3;
    public static final int JOKE = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,null);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.vp);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("头条"));
        tabLayout.addTab(tabLayout.newTab().setText("NBA"));
        tabLayout.addTab(tabLayout.newTab().setText("汽车"));
        tabLayout.addTab(tabLayout.newTab().setText("笑话"));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        MyAdapter adapter =new MyAdapter(getChildFragmentManager());
        adapter.addFragment(NewsListFragment.getNewListFragment(HEAD),"头条");
        adapter.addFragment(NewsListFragment.getNewListFragment(NBA),"NBA");
        adapter.addFragment(NewsListFragment.getNewListFragment(CAR),"汽车");
        adapter.addFragment(NewsListFragment.getNewListFragment(JOKE),"笑话");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state){
            case 0:

        }
    }

    public static class MyAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> titles = new ArrayList<>();

        MyAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
