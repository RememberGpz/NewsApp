package com.example.remember.newsapp.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.newsbeans.News;
import com.example.remember.newsapp.news.adapter.NewsListAdapter;
import com.example.remember.newsapp.news.presenter.NewsPresenterImpl;
import com.example.remember.newsapp.news.view.NewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class NewsListFragment extends Fragment implements NewsView,SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView rv_news ;
    public SwipeRefreshLayout srl_news;
    private List<News> newses = new ArrayList<>();
    private NewsPresenterImpl newsPresenterImpl;
    private NewsListAdapter newsListAdapter;

    public static NewsListFragment getNewListFragment(int tye){
        Bundle bundle = new Bundle();
        bundle.putInt("type",tye);
        NewsListFragment newsListFragment = new NewsListFragment();
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist,null);
        rv_news = (RecyclerView)view.findViewById(R.id.rv_news);
        srl_news = (SwipeRefreshLayout)view.findViewById(R.id.srl_news);
        srl_news.setColorSchemeResources(R.color.colorPrimary);
        srl_news.setOnRefreshListener(this);          //一定要为swipeReflreshLayout设置监听器，不然下拉后，setRelreshing(false)为无效；
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsPresenterImpl = new NewsPresenterImpl(this);
        rv_news.setLayoutManager(layoutManager);
        rv_news.addOnScrollListener(mOnScrollListener);
        newsListAdapter = new NewsListAdapter(getContext());
        rv_news.setAdapter(newsListAdapter);
        onRefresh();
        return view;
    }

    @Override
    public void addNews(List<News> newsList) {
        if (newses == null){
            newses = new ArrayList<>();
        }
        newses.clear();
        newses = newsList;

        newsListAdapter.setData(newses);
    }

    private RecyclerView.OnScrollListener mOnScrollListener= new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
    @Override
    public void onRefresh() {

        newsPresenterImpl.loadNewsList();

    }

    public void closeRefleshing(){
        Log.i("CloseRefleshing()","istrue");
        if (srl_news.isRefreshing()){
                    srl_news.setRefreshing(false);
            }
        }

//        srl_news.post(new Runnable() {           这样用又是为什么？？？？？？？？？？
//        @Override
//        public void run() {
//            Log.i("CloseRefleshing()","onReflesh().run");
//            srl_news.setRefreshing(false);
//        }
//      });

}
