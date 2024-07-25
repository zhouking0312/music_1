package com.example.myapplication.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.R;

import java.util.List;
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private List<MusicData_Activity> itemList;

    // 适配器构造函数，接收数据列表
    public MyListAdapter(List<MusicData_Activity> itemList) {
        this.itemList = itemList;
    }

    // 创建 ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rec,parent,false);
        return new ViewHolder(view);
    }

    // 绑定 ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicData_Activity musicDataActivity=itemList.get(position);
        holder.textView.setText(musicDataActivity.getMusicName());
        holder.textView2.setText(musicDataActivity.getAuthor());
    }

    // 返回数据项的数量
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder 类
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_name);
            textView = itemView.findViewById(R.id.list_aname);
        }
    }
}
