package com.example.remember.newsapp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.zip.Inflater;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by Administrator on 2017/9/30.
 * 自己封装的RecyclerView
 */

public class RefleshRecyclerView extends RecyclerView {
    private AutoAdapter autoAdapter;

    public RefleshRecyclerView(Context context) {
        this(context, null);
    }

    public RefleshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefleshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private boolean isLoadingMore = false;         //是否正在上拉加载
    private boolean loadMoreEnable = false;   //上拉加载是否可用
    private boolean footer_visible = false;   //脚布局是否可见
    private int footerResource = -1;  //脚布局
    private LoadMoreListner listner;

    private void init(){
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getAdapter()!=null&&getLayoutManager()!=null){
                    int lastVisibleItemPosition =( (LinearLayoutManager)getLayoutManager()).findLastVisibleItemPosition();
                    int itemCount = getAdapter().getItemCount();

                    if (itemCount>0 && distanceY <0 && lastVisibleItemPosition+1>=itemCount){
                        loading();
                        if (footerResource!=-1){
                            isLoadingMore = true;
                            getAdapter().notifyItemChanged(itemCount-1);
                        }
                        if (listner != null){
                            listner.onLoadMoreLister();
                        }
                    }
                }

            }
        });
    }
    //判断滑动的方向
    private float distanceY = 0;
    float startY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float y = ev.getRawY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY =  y;
                break;
            case MotionEvent.ACTION_MOVE:
                distanceY = y - startY;
                startY = y;
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private void loading(){
        this.isLoadingMore = true;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter);
        slideInBottomAnimationAdapter.setDuration(600);
        autoAdapter = new AutoAdapter(slideInBottomAnimationAdapter);
        super.setAdapter(adapter);
    }


    public interface LoadMoreListner{
        void onLoadMoreLister();
    }

    //设置是否允许加载更多
    public void isLoadEnable(boolean loadMoreEnable){
        this.loadMoreEnable = loadMoreEnable;
    }
    //设置脚布局
    public void setFooterResource(int footerResource){
        this.footerResource = footerResource;
    }
    //加载完成
    public void loadMoreComplete(){
        this.isLoadingMore = false;
    }

    //设置监听
    public void setLoadMoreListner(LoadMoreListner loadMoreListner){
        listner = loadMoreListner;
    }


    class AutoAdapter extends RecyclerView.Adapter<ViewHolder>{
        private Adapter adapter;
        private final int FOOTER = Integer.MAX_VALUE;

        public AutoAdapter(Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            if (position==getItemCount()-1 && loadMoreEnable && footerResource != -1 && footer_visible){
                return FOOTER;
            }else {
                return adapter.getItemViewType(position);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder = null;
            if (viewType == FOOTER){
                viewHolder = new FooterViewHoler(LayoutInflater.from(getContext()).inflate(footerResource,parent,false));
            }else {
                viewHolder = adapter.onCreateViewHolder(parent,viewType);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position)!= FOOTER){
                adapter.onBindViewHolder(holder,position);
            }
        }

        @Override
        public int getItemCount() {
            if (adapter.getItemCount()!=0){
                int count = adapter.getItemCount();
                if (footer_visible && loadMoreEnable && footerResource != -1){
                    count++;
                }
                return count;
            }
            return 0;

        }

        class FooterViewHoler extends RecyclerView.ViewHolder{
            public FooterViewHoler(View itemView) {
                super(itemView);
            }
        }
    }
}
