package com.example.remember.newsapp.picture.widget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.picture.PictureAdapter;

/**
 * Created by Administrator on 2017/8/31.
 */

public class PictureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout srl_picture;
    private RecyclerView rv_picture;
    private PictureAdapter pictureAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture,null);
        srl_picture = (SwipeRefreshLayout)view.findViewById(R.id.srl_picture);
        rv_picture = (RecyclerView)view.findViewById(R.id.rv_picture);
        srl_picture.setColorSchemeResources(R.color.colorPrimary);
        srl_picture.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_picture.setLayoutManager(linearLayoutManager);
        rv_picture.setHasFixedSize(true);
        rv_picture.setItemAnimator(new DefaultItemAnimator());
        rv_picture.addOnScrollListener(mOnScrollListener);
        pictureAdapter = new PictureAdapter();
        rv_picture.setAdapter(pictureAdapter);
        return view;
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

    }
}
