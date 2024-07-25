package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Bean.MusicInfoBean
import com.example.myapplication.Bean.SingletonMusicInfo
import com.example.myapplication.R

class FragmentAdapter(private val dataList: MutableList<MusicInfoBean>) :
    RecyclerView.Adapter<FragmentAdapter.ViewHolder>() {

    // 创建 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    // 绑定数据到 ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.fname.setText(item.musicName)
        holder.fauthor.setText(item.author)
        holder.f_x.setOnClickListener{
            dataList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    // 返回数据集的大小
    override fun getItemCount(): Int {
        return dataList.size
    }

    // ViewHolder 类，用于表示列表项的视图和数据绑定
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fname = itemView.findViewById<TextView>(R.id.fname)
        val fauthor = itemView.findViewById<TextView>(R.id.fauthor)
        val f_x = itemView.findViewById<ImageView>(R.id.f_x)
    }
}
