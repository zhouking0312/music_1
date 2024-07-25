package com.example.myapplication.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.youth.banner.adapter.BannerAdapter;


import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("unchecked")
public class BannerImageAdapter<MusicInfoBean> extends BannerAdapter<MusicInfoBean, BannerImageAdapter.BannerImageHolder> {

    public BannerImageAdapter(List<MusicInfoBean> data) {
        super(data);
    }

    @Override
    public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
        return new BannerImageHolder(view);
    }

    @Override
    public void onBindView(BannerImageHolder holder, MusicInfoBean data, int position, int size) {
        String imageUrl = ((com.example.myapplication.Bean.MusicInfoBean) data).getCoverUrl();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.imageView);
    }

    public static class BannerImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView imageView2;
        public BannerImageHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner_image);
            imageView2 = itemView.findViewById(R.id.bannerjia);

        }
    }
}


