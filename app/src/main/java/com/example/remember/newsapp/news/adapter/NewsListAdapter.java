package com.example.remember.newsapp.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.newsbeans.News;
import com.example.remember.newsapp.news.widget.NewsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remember on 2017/9/2.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<News> news = new ArrayList<>();
    private Context context;
    public NewsListAdapter(Context context){
        this.context = context;
    }
    private ItemOnClicklistener itemOnClicklistener;

    public void setData(List<News> news){
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_newslist,null);
        return new ViewHolder(view);
    }

    public void setItemOnClicklistener(ItemOnClicklistener itemOnClicklistener){
        this.itemOnClicklistener = itemOnClicklistener;
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {
        News news1 = news.get(position);
        holder.tv_newslist_title .setText(news1.getTitle());
        holder.tv_newslist_source.setText(news1.getSource());
        holder.tv_newslist_time.setText(news1.getMtime());
        Glide.with(context).load(news1.getImgsrc()).error(R.drawable.load_error).placeholder(R.drawable.default_picture).into(holder.iv_newslist_image);
    }

    public News getItem(int position) {
        return news == null ? null : news.get(position);
    }

    public interface ItemOnClicklistener{
        public void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    static class  ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_newslist_image;
        private TextView tv_newslist_title;
        private TextView tv_newslist_time;
        private TextView tv_newslist_source;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_newslist_image = itemView.findViewById(R.id.iv_newslist_image);
            tv_newslist_time = itemView.findViewById(R.id.tv_newslist_time);
            tv_newslist_source= itemView.findViewById(R.id.tv_newslist_source);
            tv_newslist_title = itemView.findViewById(R.id.tv_newslist_title);
        }
    }

}
