package com.example.remember.newsapp.picture.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.remember.newsapp.R;

/**
 * Created by Administrator on 2017/8/31.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ItemViewHolder> {

    public PictureAdapter(){

    }

    @Override
    public PictureAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ItemViewHolder(View v){
            super(v);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
