package com.example.myapplication.Adapter

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
//import com.chad.library.adapter.base.module.LoadMoreModule
import com.example.myapplication.Animator.MyAnimator
import com.example.myapplication.Bean.MusicInfoBean
import com.example.myapplication.Bean.MyData
import com.example.myapplication.Bean.SingletonMusicInfo
import com.example.myapplication.MainActivity2
import com.example.myapplication.R
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator



class MultipleItemQuickAdapter(data: MutableList<MyData>): BaseMultiItemQuickAdapter<MyData, BaseViewHolder>(data),
    LoadMoreModule {
    val myAnimator = MyAnimator()
    init{
        addItemType(MyData.ONE, R.layout.item_one)
        addItemType(MyData.TWO, R.layout.item_two)
        addItemType(MyData.THREE, R.layout.item_three)
        addItemType(MyData.FOUR, R.layout.item_four)
//        setEnableLoadMore(true)

    }

    override fun convert(holder: BaseViewHolder, item: MyData) {
        when(item.itemType){
            MyData.ONE ->{
                val mydata: MutableList<MusicInfoBean> = item.musicInfoList
                val mybanner:Banner<MusicInfoBean, MyBannerAdapter<MusicInfoBean>> = holder.itemView.findViewById(R.id.banner)
                val mybannerAdapter = MyBannerAdapter(mydata)
                mybanner.setAdapter(mybannerAdapter)
                mybanner.setIndicator(CircleIndicator(holder.itemView.context))
                mybanner.addBannerLifecycleObserver(holder.itemView.context as LifecycleOwner)
                mybanner.setOnBannerListener { data, position ->
                    SingletonMusicInfo.addMusic(item.musicInfoList[position],0)
                    start_activity()

                }
            }
            MyData.TWO ->{
                val recyclerView = holder.itemView.findViewById<RecyclerView>(R.id.horizontal_rec)
                val layoutManager = LinearLayoutManager(
                    holder.itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                recyclerView.layoutManager = layoutManager
                val adapter = MyHorizontalAdapter(item.musicInfoList)
                recyclerView.adapter = adapter
            }
            MyData.THREE ->{
                holder.setText(R.id.item_name,item.musicInfoList[0].musicName)
                val imageView:ImageView = holder.getView(R.id.item_image_u)
                Glide.with(holder.itemView)
                    .load(item.musicInfoList[0].coverUrl)
                    .transform(RoundedCorners(80))
                    .into(imageView)
                holder.setText(R.id.item_author,item.musicInfoList[0].author)
                holder.itemView.findViewById<ImageView>(R.id.item_image_u).setOnClickListener{
                    SingletonMusicInfo.addMusic(item.musicInfoList[0],0)
                    start_activity()
                }
                holder.itemView.findViewById<TextView>(R.id.buttonadd).setOnClickListener{
                    SingletonMusicInfo.addMusic(item.musicInfoList[0],null)
                    myAnimator.musicAnimator(holder.itemView.findViewById(R.id.item_image_u))
                }

            }
            MyData.FOUR ->{
                holder.setText(R.id.item_name1,item.musicInfoList[0].musicName)
                val imageView1:ImageView = holder.getView(R.id.item_image_u1)
                Glide.with(holder.itemView)
                    .load(item.musicInfoList[0].coverUrl)
                    .transform(RoundedCorners(80))
                    .into(imageView1)
                holder.setText(R.id.item_author1,item.musicInfoList[0].author)
                holder.itemView.findViewById<ImageView>(R.id.item_image_u1).setOnClickListener{
                    SingletonMusicInfo.addMusic(item.musicInfoList[0],0)
                    start_activity()
                }
                holder.itemView.findViewById<TextView>(R.id.buttonadd1).setOnClickListener{
                    SingletonMusicInfo.addMusic(item.musicInfoList[0],null)
                    myAnimator.musicAnimator(holder.itemView.findViewById(R.id.item_image_u1))
                }
                if (item.musicInfoList[1]!=null){
                    holder.setText(R.id.item_name2,item.musicInfoList[1].musicName)
                    val imageView2:ImageView = holder.getView(R.id.item_image_u2)
                    Glide.with(holder.itemView)
                        .load(item.musicInfoList[1].coverUrl)
                        .transform(RoundedCorners(80))
                        .into(imageView2)
                    holder.setText(R.id.item_author2,item.musicInfoList[1].author)
                    holder.itemView.findViewById<TextView>(R.id.buttonadd2).setOnClickListener{
                        SingletonMusicInfo.addMusic(item.musicInfoList[1],null)
                        myAnimator.musicAnimator(holder.itemView.findViewById(R.id.item_image_u2))
                    }
                    holder.itemView.findViewById<ImageView>(R.id.item_image_u2).setOnClickListener{
                        SingletonMusicInfo.addMusic(item.musicInfoList[1],0)
                        start_activity()
                    }
                }
            }
        }
    }
    fun start_activity(){
        val intent = Intent(context,MainActivity2::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(R.anim.`in`, R.anim.out)
    }

}