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

public class NewsFragment extends Fragment {
    private TabLayout tabLayout ;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,null);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.vp);
        viewPager.setOffscreenPageLimit(3);
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
        adapter.addFragment(new Fragment(),"头条");
        adapter.addFragment(new Fragment(),"NBA");
        adapter.addFragment(new Fragment(),"汽车");
        adapter.addFragment(new Fragment(),"笑话");
        viewPager.setAdapter(adapter);
    }

    class MyAdapter extends FragmentPagerAdapter{
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

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
    }
}
