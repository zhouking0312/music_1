package com.example.myapplication.day2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Adapter.LyricsAdapter;
import com.example.myapplication.Bean.MusicInfoBean;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import com.example.myapplication.Bean.MusicData_Activity;

public class BlankFragment1 extends Fragment {

    private static final String TAG = "test_BlankFragment2";
    private MusicInfoBean musicData;
//    public List<String> lyricsList = new ArrayList<>();

    public BlankFragment1() {
        super(R.layout.fragment_1);
//        this.musicData = musicData;MusicData_Activity musicData
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        MusicInfoBean musicData = new MusicInfoBean();
        musicData.setMusicName("测试1");
        musicData.setCoverUrl("http://p2.music.126.net/5NsE7E5efsMuA0UP9jtLBw==/109951169663480470.jpg");
        musicData.setAuthor("测试2");
        musicData.setLyricUrl("https://cdn.cnbj1.fds.api.mi-img.com/migame-monitor-public/music/lrc/4.txt");

        View view=inflater.inflate(R.layout.fragment_1, container, false);
        ImageView imageView = view.findViewById(R.id.musicfragment_image);

        Glide.with(getContext())
                .load(musicData.getCoverUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
        setAnimator(imageView);

//        try {
//            List<String> lyricsList = fetchLyricsFromURL(musicData.getLyricUrl());
//            if(lyricsList!=null){
//                RecyclerView recyclerView = view.findViewById(R.id.musicfragment_rec);
//                LyricsAdapter adapter = new LyricsAdapter(lyricsList);
//                recyclerView.setAdapter(adapter);
//                imageView.setVisibility(View.GONE);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        return view;
    }



    public void change(ImageView imageView,RecyclerView recyclerView){
        if(imageView.getVisibility()==View.GONE){
            imageView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    public void setAnimator(ImageView imageView){
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 360f);
        rotateAnimator.setDuration(40000); // 设置动画持续时间，单位毫秒
        rotateAnimator.setInterpolator(new LinearInterpolator()); // 设置线性插值器，使动画匀速旋转
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimator); // 将旋转动画加入 AnimatorSet
        animatorSet.start(); // 启动动画
    }


    public static List<String> fetchLyricsFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        // 设置连接超时和读取超时时间
        conn.setConnectTimeout(5000); // 设置连接超时为5秒
        conn.setReadTimeout(5000); // 设置读取超时为5秒

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            List<String> lyricsList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lyricsList.add(line);
            }
            reader.close();
            return lyricsList;
        } else {
            throw new IOException("HTTP error code: " + responseCode);
        }
    }
}