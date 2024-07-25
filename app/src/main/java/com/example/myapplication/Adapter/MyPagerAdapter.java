package com.example.myapplication.Adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.MusicInfoBean;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;

    private List<MusicInfoBean> data;

    public MyPagerAdapter(ArrayList<View> viewLists, List<MusicInfoBean> data) {
        super();
        this.viewLists = viewLists;
        this.data=data;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("viewpager",data.get(position).getCoverUrl());
        ImageView imageView=viewLists.get(position).findViewById(R.id.viewpager_image);
        Glide.with(viewLists.get(position))
                .load(data.get(position).getCoverUrl())
                .into(imageView);
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}

