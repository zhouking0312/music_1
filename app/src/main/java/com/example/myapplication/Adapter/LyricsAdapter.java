package com.example.myapplication.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.ViewHolder> {
    private List<String> date;
    private int currentHighlightedPosition = -1;
    public LyricsAdapter(List<String> date){
        this.date=date;
    }


    @NonNull
    @Override
    public LyricsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyrics_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int i){
        String lyrics=date.get(i);
        Log.d("adapter",lyrics);
        holder.mlyrics.setText(lyrics);
        if (i == currentHighlightedPosition) {
            // 设置当前项的特殊样式，例如高亮显示
            holder.mlyrics.setTextColor(Color.RED);
        } else {
            // 恢复默认样式
            holder.mlyrics.setTextColor(Color.BLACK);
        }
    }
    public void updateCurrentItem(int newPosition) {
        int previousPosition = currentHighlightedPosition;
        currentHighlightedPosition = newPosition;

        // 刷新之前高亮项和新高亮项，以便更新视图
        notifyItemChanged(previousPosition);
        notifyItemChanged(currentHighlightedPosition);
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView mlyricsIcon;
        TextView mlyrics;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
//            mlyricsIcon = itemView.findViewById(R.id.lyrics_icon);
            mlyrics = itemView.findViewById(R.id.lyrics);
        }
    }
    public void clear() {
        date.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount(){
        return date.size();
    }
}