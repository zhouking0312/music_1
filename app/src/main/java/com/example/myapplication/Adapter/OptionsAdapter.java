package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Bean.MusicData_Activity;

import java.util.List;
public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {

    private List<MusicData_Activity> musicList;
    private OnItemClickListener listener;

    public OptionsAdapter(List<MusicData_Activity> musicList, OnItemClickListener listener) {
        this.musicList = musicList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rec, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MusicData_Activity item = musicList.get(position);
        holder.listNameTextView.setText(item.getMusicName());
        holder.listANameTextView.setText(item.getAuthor());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView listNameTextView;
        TextView listANameTextView;
        ImageView imageView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            listNameTextView = itemView.findViewById(R.id.list_name);
            listANameTextView = itemView.findViewById(R.id.list_aname);
            imageView = itemView.findViewById(R.id.list_image);

            // 设置 ImageView 的点击事件监听器
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // 调用接口方法，执行操作
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    // 接口定义点击事件
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
