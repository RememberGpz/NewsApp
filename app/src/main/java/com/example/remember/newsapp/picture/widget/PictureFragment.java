package com.example.remember.newsapp.picture.widget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.remember.newsapp.R;
import com.example.remember.newsapp.beans.Picture;
import com.example.remember.newsapp.picture.adapter.PictureAdapter;
import com.example.remember.newsapp.picture.net.NewWord;
import com.example.remember.newsapp.picture.presenter.PicturePresenterImpl;
import com.example.remember.newsapp.picture.view.PictureView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class PictureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout srl_picture;
    private RecyclerView rv_picture;
    private PictureAdapter pictureAdapter;
    private List<Picture> mPictures = new ArrayList<>();
    private StaggeredGridLayoutManager staggeredGLM;
    private PicturePresenterImpl picturePresenterImpl;
    private int contentNum = 10;


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
        staggeredGLM = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_picture.setLayoutManager(staggeredGLM);
        rv_picture.setHasFixedSize(true);
        rv_picture.setItemAnimator(new DefaultItemAnimator());
        rv_picture.addOnScrollListener(mOnScrollListener);
        pictureAdapter = new PictureAdapter(getContext());
        pictureAdapter.setOnItemClickListener(new PictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

        picturePresenterImpl = new PicturePresenterImpl(this);

        rv_picture.setAdapter(pictureAdapter);

        setData(false);
        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener= new RecyclerView.OnScrollListener() {
        int []lastVisibleItem;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem[1] +1 == staggeredGLM.getItemCount()){
                setData(true);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = staggeredGLM.findLastVisibleItemPositions(new int[staggeredGLM.getSpanCount()]);
        }
    };

    private void setData(boolean isFirst){
        if (isFirst) contentNum= contentNum + 6;

        Call<Picture> call = NewWord.getGankAPI().getGankData("福利", contentNum, 1);

        call.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                List<Picture.ResultsBean> pictures= response.body().getResults();
                pictureAdapter.setPicturesData(pictures);
                srl_picture.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Picture> call, Throwable t) {
                Snackbar.make(rv_picture,"Load Error",Snackbar.LENGTH_SHORT).show();
                srl_picture.setRefreshing(false);
            }
        });
    }


    @Override
    public void onRefresh() {
        setData(true);
    }

//    @Override
//    public void addPicture(List<Picture> pictures) {
//        Log.i("PictureFragment","addpicture");
//        if (mPictures == null){
//            mPictures = new ArrayList<>();
//        }
//        mPictures.clear();
//        mPictures = pictures;
//        srl_picture.setRefreshing(false);
//        pictureAdapter.setData(mPictures);
//    }
}
