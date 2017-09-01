package com.example.remember.newsapp.picture.adapter;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.utils.ToolsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ItemViewHolder> {
    private Context context;
    private int mMaxWidth;
    private int mMaxHeight;
    private List<Picture> pictures =  new ArrayList<>();
    public PictureAdapter(Context context){
        this.context = context;
        mMaxWidth = ToolsUtil.getWidthInPx(context) - 20;
        mMaxHeight = ToolsUtil.getHeightInPx(context) - ToolsUtil.getStatusHeight(context) -
                ToolsUtil.dip2px(context, 96);
    }

    public void setData(List<Picture> pictures){
        this.pictures = pictures;
        notifyDataSetChanged();
    }

    @Override
    public PictureAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture,parent,false);
        return new ItemViewHolder(view);
}

    @Override
    public void onBindViewHolder(PictureAdapter.ItemViewHolder holder, int position) {
        holder.title .setText(pictures.get(position).getTitle());
        int height;
        if (pictures.get(position).getWidth()!=null) {
            float scale = Float.parseFloat(pictures.get(position).getWidth()) / (float) mMaxWidth;
            height = (int)(Integer.parseInt( pictures.get(position).getHeight()) / scale);
            if(height > mMaxHeight) {
                height = mMaxHeight;
            }
            holder.picture.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth, height));
        }


        Glide.with(context).load(pictures.get(position).getThumburl()).placeholder(R.drawable.default_picture).error(R.drawable.load_error).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        Log.i("PictureAdapter","getItemCount");
        return pictures.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView picture;

        public ItemViewHolder(View v){
            super(v);
            title = v.findViewById(R.id.tv_title);
            picture = v.findViewById(R.id.iv_picture);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
