package com.example.remember.newsapp.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.remember.newsapp.Commons.Urls;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.newsbeans.News;
import com.example.remember.newsapp.news.adapter.NewsListAdapter;
import com.example.remember.newsapp.news.presenter.NewsPresenterImpl;
import com.example.remember.newsapp.news.view.NewsView;
import com.example.remember.newsapp.widget.RefleshRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class NewsListFragment extends Fragment implements NewsView,SwipeRefreshLayout.OnRefreshListener{

    private RefleshRecyclerView rrv;
    public SwipeRefreshLayout srl_news;
    private List<News> newses = new ArrayList<>();
    private NewsPresenterImpl newsPresenterImpl;
    private NewsListAdapter newsListAdapter;
    private int mType = NewsFragment.HEAD;
    private int pagerIndex=0;
    private LinearLayoutManager layoutManager;
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
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist,null);
        rrv = (RefleshRecyclerView)view.findViewById(R.id.rrv);
        srl_news = (SwipeRefreshLayout)view.findViewById(R.id.srl_news);
        srl_news.setColorSchemeResources(R.color.colorPrimary);
        srl_news.setOnRefreshListener(this);          //一定要为swipeReflreshLayout设置监听器，不然下拉后，setRelreshing(false)为无效；
        layoutManager = new LinearLayoutManager(getActivity());
        newsPresenterImpl = new NewsPresenterImpl(this);
        rrv.setEnabled(true);
        rrv.setLoadMoreListner(new RefleshRecyclerView.LoadMoreListner() {
            @Override
            public void onLoadMoreLister() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rrv.loadMoreComplete();
                    }
                },1500);
            }
        });
        rrv.setFooterResource(R.layout.item_rrv_footer);
        rrv.setLayoutManager(layoutManager);
        rrv.isLoadEnable(true);
        rrv.addOnScrollListener(mOnScrollListener);
        newsListAdapter = new NewsListAdapter(getContext());
        newsListAdapter.setItemOnClicklistener(itemOnClicklistener);
        rrv.setAdapter(newsListAdapter);
        onRefresh();
        return view;
    }

    @Override
    public void addNews(List<News> newsList) {
        if (newses == null){
            newses = new ArrayList<>();
        }
        newses.clear();
        newses.addAll(newsList);
        newsListAdapter.setData(newses);
    }

    private NewsListAdapter.ItemOnClicklistener itemOnClicklistener = new NewsListAdapter.ItemOnClicklistener() {
        @Override
        public void onItemClick(View view, int position) {
            News news = newsListAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("title",news.getTitle());
            intent.putExtra("docid",news.getDocId());
            intent.putExtra("imgsrc",news.getImgsrc());
            startActivity(intent);

        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener= new RecyclerView.OnScrollListener() {
        int lastVisibleItem;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
    @Override
    public void onRefresh() {
        DataSupport.deleteAll(News.class);
        newsPresenterImpl.loadNewsList(mType,pagerIndex);

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
