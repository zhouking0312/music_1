package com.example.myapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapplication.Animator.MyAnimator
import com.example.myapplication.Bean.MusicInfoBean
import com.example.myapplication.Bean.SingletonMusicInfo
import com.example.myapplication.MainActivity2
import com.example.myapplication.R

class MyHorizontalAdapter(
    private val mData: List<MusicInfoBean>,
) : RecyclerView.Adapter<MyHorizontalAdapter.ViewHolder?>() {

    val myAnimator = MyAnimator()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.horizontal_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        Glide.with(holder.imageView)
            .load(item.coverUrl)
            .transform(RoundedCorners(130))
            .into(holder.imageView)
        holder.imageView.setOnClickListener{
            SingletonMusicInfo.addMusic(item,0)
            val intent = Intent(holder.imageView.context,MainActivity2::class.java)
            holder.itemView.context.startActivity(intent)
        }
        holder.imageViewadd.setOnClickListener{
            SingletonMusicInfo.addMusic(item,0)
            myAnimator.musicAnimator(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.viewpager_image)
        var imageViewadd : TextView = itemView.findViewById(R.id.add)
    }
}