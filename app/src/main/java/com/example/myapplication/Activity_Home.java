package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.myapplication.Adapter.MultipleItemQuickAdapter;
import com.example.myapplication.Adapter.MyListAdapter;
import com.example.myapplication.Adapter.OptionsAdapter;
import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.Bean.RecordBean;
import com.example.myapplication.Bean.MyItem;
import com.example.myapplication.Bean.MyItem2;
import com.example.myapplication.Bean.ResponseBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.day2.MainActivity_music;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Home extends AppCompatActivity {
    public ApiInterface apiInterface;
    public List<RecordBean> records=new ArrayList<>();
    public List<MyItem> data=new ArrayList<>();
    public List<MyItem2> data2=new ArrayList<>();
    public MultipleItemQuickAdapter adapter;
    public MyListAdapter adapter2;
    public OptionsAdapter adapter3;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "MainActivity_Home";
    private MediaPlayer mediaPlayer;
    public ImageView imageView;
    public TextView textViewname;
    public TextView textViewaname;
    public ImageView imageView2;
    public ImageView all_list;
    public TextView music_view;
//    public ArrayList<MusicData_Activity> musicList;
    public int i=3;
    public ArrayList<MusicData_Activity> musicListhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        imageView=findViewById(R.id.musicfragment_image);
        textViewname=findViewById(R.id.name);
        textViewaname=findViewById(R.id.a_name);
        imageView2=findViewById(R.id.music_play);
        all_list=findViewById(R.id.all_list);
        music_view=findViewById(R.id.music_view);



        Intent intent = getIntent();
        musicListhome = intent.getParcelableArrayListExtra("musicList");
        if (musicListhome != null && !musicListhome.isEmpty()){
            Glide.with(imageView.getContext())
                    .load(musicListhome.get(0).getCoverUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
            textViewname.setText(musicListhome.get(0).getMusicName());
            textViewaname.setText(musicListhome.get(0).getAuthor());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Activity_Home.this, MainActivity_music.class);
                    intent.putParcelableArrayListExtra("musicList", musicListhome);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
                }
            });

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            try {
                mediaPlayer.setDataSource(musicListhome.get(i).getMusicUrl());
                mediaPlayer.prepare();
            } catch (IOException e) {
                Log.e("errrr", e.getMessage());
            }
        }

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView2.isSelected()) {
                    imageView2.setSelected(!imageView2.isSelected());
                    mediaPlayer.pause();
                } else {
                    imageView2.setSelected(!imageView2.isSelected());
                    mediaPlayer.start();
                }

            }
        });

        recyclerView2 =findViewById(R.id.list_rec);
        recyclerView2.setLayoutManager(new LinearLayoutManager(imageView.getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hotfix-service-prod.g.mi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface=retrofit.create(ApiInterface.class);
        get(1,4,1);

        recyclerView =findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MultipleItemQuickAdapter(data2);
        adapter.setOnItemClickListener(new MultipleItemQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ArrayList<MusicData_Activity> musicList) {
                musicListhome = musicList;
                Glide.with(imageView.getContext())
                        .load(musicList.get(position).getCoverUrl())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(imageView);
                textViewname.setText(musicList.get(position).getMusicName());
                textViewaname.setText(musicList.get(position).getAuthor());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent intent=new Intent(Activity_Home.this, MainActivity_music.class);
                            intent.putParcelableArrayListExtra("musicList", musicListhome);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
        all_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });
        swipeRefreshLayout=findViewById(R.id.swip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get(i,4,1);
                swipeRefreshLayout.setRefreshing(false);
                i++;
            }
        });
        //下拉刷新
        int j=5;
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                get(1,j,2);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public void onItemClick(int position, ArrayList<MusicData_Activity> musicList) {
//        Glide.with(this)
//                .load(musicList.get(position).getCoverUrl())
//                .into(imageView);
//        textViewname.setText(musicList.get(position).getMusicName());
//        textViewaname.setText(musicList.get(position).getAuthor());
//    }

    public void get(int current,int size,int text){
        Call<ResponseBean> call = apiInterface.getString(current,size);
        call.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseBean gameResponseBean = response.body();
                    Log.d(TAG, "网络请求成功1：" + gameResponseBean);
                    records = gameResponseBean.getDataBean().getRecordsBean();
                    if(text==1){
                        data_set2(records);
                    }
                    if (text==2){
                        data_add2(records);
                    }
                    Log.d("home","sty"+records.get(0).getStyle());
                } else {
                    Log.d(TAG, "网络请求成功，但无响应内容");
                }
            }
            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Log.e(TAG, "网络请求失败", t);
            }
        });

    }

//    public void data_set(List<RecordBean> records){
//        int i;
//        for(RecordBean record:records){
//            i=1;
//            for (MusicInfoBean musicInfoBean:record.getMusicInfoList()){
//                switch (record.getStyle()){
//                    case 1:
//                        data.add(0,new MyItem(record.getStyle(),
//                                musicInfoBean.getMusicName(),
//                                musicInfoBean.getCoverUrl(),
//                                musicInfoBean.getAuthor()));
//                        break;
//                    case 2:
//                        data.add(i*2-1,new MyItem(record.getStyle(),
//                                musicInfoBean.getMusicName(),
//                                musicInfoBean.getCoverUrl(),
//                                musicInfoBean.getAuthor()));
//                    case 3:
//                        data.add(i*3-1,new MyItem(record.getStyle(),
//                                musicInfoBean.getMusicName(),
//                                musicInfoBean.getCoverUrl(),
//                                musicInfoBean.getAuthor()));
//                    case 4:
//                        MyItem myItem=new MyItem(record.getStyle(),
//                                musicInfoBean.getMusicName(),
//                                musicInfoBean.getCoverUrl(),
//                                musicInfoBean.getAuthor());
//                        data.add(i*4-1,myItem);
//                }i++;
//            }
//        }adapter.notifyDataSetChanged();
//    }


    public void data_set2(List<RecordBean> records){
        for(RecordBean record:records){
                switch (record.getStyle()){
                    case 1:
                        data2.add(0,new MyItem2(record.getStyle(), record.getMusicInfoList()));
                        break;
                    case 2:
                        data2.add(1,new MyItem2(record.getStyle(), record.getMusicInfoList()));
                    case 3:
                        data2.add(2,new MyItem2(record.getStyle(),record.getMusicInfoList()));
                    case 4:
                        data2.add(3,new MyItem2(record.getStyle(),record.getMusicInfoList()));
            }adapter.notifyDataSetChanged();
        }
    }

    public void data_add2(List<RecordBean> records){
        for(RecordBean record:records){
            switch (record.getStyle()){
                case 1:
                    data2.add(new MyItem2(record.getStyle(), record.getMusicInfoList()));
                    break;
                case 2:
                    data2.add(new MyItem2(record.getStyle(), record.getMusicInfoList()));
                case 3:
                    data2.add(new MyItem2(record.getStyle(),record.getMusicInfoList()));
                case 4:
                    data2.add(new MyItem2(record.getStyle(),record.getMusicInfoList()));
            }adapter.notifyDataSetChanged();
        }
    }


//    private void showOptionsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(all_list.getContext());
//        builder.setTitle("当前播放:");
//        List<String> items = new ArrayList<>();
//        for (MusicData_Activity musicDataActivity : musicListhome) {
//            items.add(musicDataActivity.getMusicName());
//        }
//
//        final CharSequence[] itemsArray = items.toArray(new CharSequence[items.size()]);
//
//        builder.setItems(itemsArray, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                // 处理选项点击事件
//                switch (i) {
//                    case 0:
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        // 创建并显示对话框
//        AlertDialog alertDialog = builder.create();
//        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
//        alertDialog.show();
//    }

    private void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(all_list.getContext());

        View dialogView = LayoutInflater.from(all_list.getContext()).inflate(R.layout.layout_list, null);
        builder.setView(dialogView);

        builder.setTitle("当前播放:");
        builder.setTitle("收藏数:"+musicListhome.size());

        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view_options);

        LinearLayoutManager layoutManager = new LinearLayoutManager(all_list.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter3 = new OptionsAdapter(musicListhome, new OptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 处理点击事件逻辑，例如移除某项数据
                musicListhome.remove(position);
                adapter3.notifyItemRemoved(position);
            }
        });
        recyclerView.setAdapter(adapter3);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
//        alertDialog.getWindow().setPadding(0, 0, 0, 0);
        alertDialog.show();

    }

}