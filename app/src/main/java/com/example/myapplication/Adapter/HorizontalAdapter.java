package com.example.myapplication.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.Bean.MusicInfoBean;
import com.example.myapplication.R;
import com.example.myapplication.day2.MainActivity_music;

import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    private List<MusicInfoBean> mData;
    private ArrayList<MusicData_Activity> musicList;
    private OnItemClickListener mListener;

    public HorizontalAdapter(List<MusicInfoBean> data,ArrayList<MusicData_Activity> musicList,OnItemClickListener listener) {
        this.mData = data;
        this.musicList = musicList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_one, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MusicInfoBean item = mData.get(position);
        Glide.with(holder.imageView)
                .load(item.getCoverUrl())
                .transform(new RoundedCorners(130))
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        OnItemClickListener clickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.viewpager_image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
        public void setClickListener(OnItemClickListener listener) {
            this.clickListener = listener;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

