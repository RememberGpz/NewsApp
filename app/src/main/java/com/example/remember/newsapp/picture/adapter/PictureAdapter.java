package com.example.remember.newsapp.picture.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.textclassifier.TextClassification;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.utils.ToolsUtil;
import com.example.remember.newsapp.widget.RatioImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/8/31.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ItemViewHolder> implements View.OnClickListener{
    private Context context;
    private List<Picture.ResultsBean> pictures =  new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    public PictureAdapter(Context context){
        this.context = context;
    }

    public void setPicturesData(List<Picture.ResultsBean> pictures){
        this.pictures = pictures;
        notifyDataSetChanged();
    }

    @Override
    public PictureAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture,parent,false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
}

    @Override
    public void onBindViewHolder(PictureAdapter.ItemViewHolder holder, int position) {
        holder.title.setText(pictures.get(position).getDesc());
        holder.itemView.setTag(position);
        Glide.with(context).load(pictures.get(position).getUrl())
                .fitCenter()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_picture).error(R.drawable.load_error)
                .into(holder.picture);


    }

    @Override
    public int getItemCount() {
        Log.i("PictureAdapter","getItemCount");
        return pictures.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RatioImageView picture;

        public ItemViewHolder(View v){
            super(v);
            title = v.findViewById(R.id.tv_title);
            picture = v.findViewById(R.id.iv_picture);
            picture.setOriginalSize(50,50);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onClick(View view) {
        onItemClickListener.onItemClick(view,(int)view.getTag());
    }
}
