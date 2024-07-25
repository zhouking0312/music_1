package com.example.myapplication.Adapter;

//import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
//import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.Bean.MusicInfoBean;
import com.example.myapplication.Bean.MyItem;
import com.example.myapplication.Bean.MyItem2;
import com.example.myapplication.R;
import com.example.myapplication.day2.MainActivity_music;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MyItem2, BaseViewHolder> implements LoadMoreModule , HorizontalAdapter.OnItemClickListener  {
    private static final String TAG = "test_Adapter";
    int i=0;
    List<String> allname=new ArrayList<>();

    ArrayList<MusicData_Activity> musicList = new ArrayList<>();

    public MultipleItemQuickAdapter(List<MyItem2> data2){
        super(data2);
        addItemType(MyItem.ONE,R.layout.item_banner);
        addItemType(MyItem.TWO,R.layout.item_horizontal_card);
        addItemType(MyItem.THREE,R.layout.item_uniseriate);
        addItemType(MyItem.FOUR,R.layout.item_doublecolumns);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder holder, MyItem2 item) {
        switch (holder.getItemViewType()) {
            case MyItem.ONE:
                List<MusicInfoBean> mydata = item.getMusicInfoList();
                Banner banner=holder.itemView.findViewById(R.id.banner);
                BannerImageAdapter<MusicInfoBean> bannerImageAdapter = new BannerImageAdapter<>(mydata);
                banner.setAdapter(bannerImageAdapter);
                banner.setIndicator(new CircleIndicator(holder.itemView.getContext())); // 设置指示器
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        musicList.add(0,new MusicData_Activity(item.getMusicInfoList().get(position)));
                        Toast.makeText(getContext(), item.getMusicInfoList().get(position).getMusicName(), Toast.LENGTH_SHORT).show();
                        performItemClick(position,musicList);
                        change();
                    }
                });
                banner.addBannerLifecycleObserver((LifecycleOwner) holder.itemView.getContext());

//                banner1.setAdapter(bannerImageAdapter); // 与 banner 使用同一个 adapter
//                banner1.setIndicator(new CircleIndicator(holder.itemView.getContext())); // 设置指示器，如果需要的话
//                banner1.setOnBannerListener(new OnBannerListener() {
//                    @Override
//                    public void OnBannerClick(Object data, int position) {
//                        // 处理 banner1 的点击事件
//                        Toast.makeText(getContext(), "Banner1 item clicked: " + position, Toast.LENGTH_SHORT).show();
//                        // 如果需要执行其他操作，可以在这里添加代码
//                    }
//                });
//                banner1.addBannerLifecycleObserver((LifecycleOwner) holder.itemView.getContext());
                break;
            case MyItem.TWO:
                RecyclerView recyclerView = holder.itemView.findViewById(R.id.horizontal_rec);
                List<MusicInfoBean> mData = new ArrayList<>();
                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(layoutManager);
                mData = item.getMusicInfoList();
                HorizontalAdapter adapter = new HorizontalAdapter(mData,musicList,new HorizontalAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(int position) {
                        change();
                    }
                });
                recyclerView.setAdapter(adapter);




                break;
            case MyItem.THREE:
                List<MusicInfoBean> musicInfoBeans = item.getMusicInfoList();
                String name=musicInfoBeans.get(0).getMusicName();
                Log.d("music3",musicInfoBeans.get(0).getMusicName());
                holder.setText(R.id.item_name, musicInfoBeans.get(0).getMusicName());
                ImageView imageView=holder.itemView.findViewById(R.id.item_image_u);
                Glide.with(holder.itemView)
                        .load(musicInfoBeans.get(0).getCoverUrl())
//                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(radius, margin)))
                        .transform(new RoundedCorners(80))
                        .into(imageView);
                holder.setText(R.id.item_author, musicInfoBeans.get(0).getAuthor());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        musicList.add(0,new MusicData_Activity(musicInfoBeans.get(0)));
                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                        performItemClick(0,musicList);
                        change();
                    }
                });
                holder.itemView.findViewById(R.id.buttonadd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rotate(holder.itemView.findViewById(R.id.item_image_u));
                        musicList.add(0,new MusicData_Activity(musicInfoBeans.get(0)));
                        performItemClick(0,musicList);
                        Toast.makeText(getContext(),"将"+name+"添加到⾳乐列表；" , Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case MyItem.FOUR:
                List<MusicInfoBean> musicInfoBeans4 = item.getMusicInfoList();
                if(musicInfoBeans4.get(1)!=null){
                    Log.d("music","aaaa");
                    holder.setText(R.id.item_name1, item.getMusicInfoList().get(0).getMusicName());
                    ImageView imageView4=holder.itemView.findViewById(R.id.item_image_u1);
                    Glide.with(holder.itemView)
                            .load(item.getMusicInfoList().get(0).getCoverUrl())
//                            .transform(new RoundedCorners(80))
                            .into(imageView4);
                    holder.setText(R.id.item_author1, item.getMusicInfoList().get(0).getAuthor());


                    holder.setText(R.id.item_name2, item.getMusicInfoList().get(1).getMusicName());
                    ImageView imageView5=holder.itemView.findViewById(R.id.item_image_u2);
                    Glide.with(holder.itemView)
                            .load(item.getMusicInfoList().get(1).getCoverUrl())
                            .into(imageView5);
                    holder.setText(R.id.item_author2, item.getMusicInfoList().get(0).getAuthor());
                }
                else {
                    Log.d("music","aaaa");
                    holder.setText(R.id.item_name1, item.getMusicInfoList().get(0).getMusicName());
                    ImageView imageView4=holder.itemView.findViewById(R.id.item_image_u1);
                    Glide.with(holder.itemView)
                            .load(item.getMusicInfoList().get(0).getCoverUrl())
                            .into(imageView4);
                    holder.setText(R.id.item_author1, item.getMusicInfoList().get(0).getAuthor());

                    Log.d("music","aaaa");
                    holder.setText(R.id.item_name2, item.getMusicInfoList().get(0).getMusicName());
                    ImageView imageView5=holder.itemView.findViewById(R.id.item_image_u2);
                    Glide.with(holder.itemView)
                            .load(item.getMusicInfoList().get(0).getCoverUrl())
                            .into(imageView5);
                    holder.setText(R.id.item_author2, item.getMusicInfoList().get(0).getAuthor());
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        musicList.add(0,new MusicData_Activity(item.getMusicInfoList().get(0)));
                        Toast.makeText(getContext(), item.getMusicInfoList().get(0).getMusicName(), Toast.LENGTH_SHORT).show();
//                        performItemClick(0,musicList);
                        change();
                        ImageView imageView_rotate=holder.itemView.findViewById(R.id.item_image_u1);

                    }
                });
                holder.itemView.findViewById(R.id.buttonadd1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rotate(holder.itemView.findViewById(R.id.item_image_u1));
                        musicList.add(0,new MusicData_Activity(item.getMusicInfoList().get(0)));
                        performItemClick(0,musicList);
                        Toast.makeText(getContext(),"将"+item.getMusicInfoList().get(0).getMusicName()+"添加到⾳乐列表；" , Toast.LENGTH_SHORT).show();
                    }
                });
                holder.itemView.findViewById(R.id.buttonadd2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rotate(holder.itemView.findViewById(R.id.item_image_u2));
                        musicList.add(0,new MusicData_Activity(item.getMusicInfoList().get(0)));
                        performItemClick(0,musicList);
                        Toast.makeText(getContext(),"将"+item.getMusicInfoList().get(0).getMusicName()+"添加到⾳乐列表；" , Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }
    public void change(){
        Intent intent=new Intent(getContext(), MainActivity_music.class);
        intent.putParcelableArrayListExtra("musicList", musicList);
        getContext().startActivity(intent);
        ((Activity) getContext()).overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ArrayList<MusicData_Activity> musicList);
    }
    public OnItemClickListener listener;
    public void performItemClick(int position, ArrayList<MusicData_Activity> musicList) {
        if (listener != null) {
            listener.onItemClick(position, musicList);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void rotate(ImageView imageView){
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 1.2f, 1.0f);
        // 创建一个沿Y轴旋转360度的动画
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f);
        // 设置动画持续时间
        scaleXAnimator.setDuration(1000);
        scaleYAnimator.setDuration(1000);
        rotateAnimator.setDuration(1000);
        // 创建动画集合，同时播放放大缩小和旋转动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator, rotateAnimator);
        // 启动动画
        animatorSet.start();
    }

    @Override
    public void onItemClick(int position) {
//        MusicData_Activity musicData = musicList.get(position);
        change();
    }

}
