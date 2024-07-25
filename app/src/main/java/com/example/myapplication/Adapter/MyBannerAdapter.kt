package com.example.myapplication.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Bean.MusicInfoBean
import com.example.myapplication.R
import com.youth.banner.adapter.BannerAdapter

class MyBannerAdapter<MusicInfoBean>(data: MutableList<MusicInfoBean>) :
    BannerAdapter<MusicInfoBean, MyBannerAdapter.MyBannerHolder>(data) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): MyBannerHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        return MyBannerHolder(view)
    }

    override fun onBindView(
        holder: MyBannerHolder,
        data: MusicInfoBean,
        position: Int,
        size: Int
    ) {
        val url :String = (data as com.example.myapplication.Bean.MusicInfoBean).coverUrl
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.imageView)
    }

    class MyBannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.banner_image)
        var imageView2: ImageView = itemView.findViewById<ImageView>(R.id.bannerjia)
    }
}