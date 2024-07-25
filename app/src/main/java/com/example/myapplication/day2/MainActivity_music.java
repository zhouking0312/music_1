package com.example.myapplication.day2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;


import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

//import com.example.myapplication.Bean.MusicData_Activity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.Activity_Home;
import com.example.myapplication.Adapter.LyricsAdapter;
import com.example.myapplication.Adapter.OptionsAdapter;
import com.example.myapplication.Bean.MusicData_Activity;
import com.example.myapplication.Bean.MusicInfoBean;
import com.example.myapplication.MusicService;
import com.example.myapplication.R;
import com.example.myapplication.ViewPagerAdapter;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import java.io.InputStream;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.BitmapFactory;

public class MainActivity_music extends AppCompatActivity {


    private TextView textViewCurrentTime;
    private TextView textViewTotalTime;
    private int songDuration = 0;
    private int currentProgress = 0;
    public Intent serviceIntent;
    public ImageView mod1;
    public ImageView mod2;
    public ImageView mod3;
    public TextView textView_name;
    public TextView textView_a;
    private MediaPlayer mediaPlayer;
    public ImageView imageView1;
    public ImageView imageView2;
    public ImageView imageView3;
    public ImageView imageView4;
    public ImageView imageView_x;
    public AnimatorSet animatorSet;
    public ImageView imageView_like;
    public ObjectAnimator rotateAnimator;
    public List<String> lyricsList = new ArrayList<>();
    public LyricsAdapter adapter;
    public int i = 0;
    public int Sty;
    public OptionsAdapter adapter3;
    public RecyclerView recyclerView;
    public ImageView imageViewmod;
    ArrayList<MusicData_Activity> receivedMusicList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mod1=findViewById(R.id.mod_1);
        mod2=findViewById(R.id.mod_2);
        mod3=findViewById(R.id.mod_3);
        imageView_x=findViewById(R.id.music_x);
        imageViewmod =findViewById(R.id.listmod);
        imageView_like=findViewById(R.id.music_like);
//        Intent intent = getIntent();
//        ArrayList<MusicData_Activity> receivedMusicList = intent.getParcelableArrayListExtra("musicList");
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
//        ViewPager2 viewPager = findViewById(R.id.viewpager_music);
//        viewPager.setAdapter(viewPagerAdapter);


        //初始化
        Intent intent = getIntent();
        receivedMusicList = intent.getParcelableArrayListExtra("musicList");


        imageViewmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });
//        Log.d("Intent",receivedMusicList.get(0).getCoverUrl());
        recyclerView = findViewById(R.id.musicfragment_rec);
        textView_name = findViewById(R.id.music_name);
        textView_a = findViewById(R.id.music_author);
        textView_name.setText(receivedMusicList.get(i).getMusicName());
        textView_a.setText(receivedMusicList.get(i).getAuthor());
        //初始化
        new LoadImageTask().execute(receivedMusicList.get(i).getCoverUrl());

        imageView1 =findViewById(R.id.musicfragment_image);
        Glide.with(this)
                .load(receivedMusicList.get(i).getCoverUrl())
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .transform(new CircleCrop())
                .into(imageView1);

//        Glide.with(this)
//                .asBitmap()
//                .load(receivedMusicList.get(i).getCoverUrl())
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
//
//                    }
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                    }
//                });

        setAnimator(imageView1);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
        try {
            mediaPlayer.setDataSource(receivedMusicList.get(i).getMusicUrl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e("errrr", e.getMessage());
        }

//        serviceIntent = new Intent(this, MusicService.class);
//        serviceIntent.putExtra("musicUrl", receivedMusicList.get(i).getMusicUrl());
//        startService(serviceIntent);

        imageView2 = findViewById(R.id.music_play);
        imageView3 = findViewById(R.id.music_l);
        imageView4 = findViewById(R.id.music_r);
        setAnimator(imageView1);
        mediaPlayer.start();
        animatorSet.start();
        imageView2.setSelected(true);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setVisibility(View.GONE);
                new DownloadLyricsTask().execute(receivedMusicList.get(i).getLyricUrl());
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("recyclerView","recyclerView");
                recyclerView.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animatorSet.isRunning()) {
                    animatorSet.cancel();
                    mediaPlayer.pause();
                    rotateAnimator.cancel();
                } else {
                    setAnimator(imageView1);
                    mediaPlayer.start();
                    animatorSet.start();
                }
                imageView2.setSelected(animatorSet.isRunning());
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_r();
//                try {
//                    mediaPlayer.setDataSource(receivedMusicList.get(i).getMusicUrl());
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            mediaPlayer.start();
//                        }
//                    });
//                    mediaPlayer.prepareAsync();
//                } catch (IOException e) {
//                    Log.e("errrr", e.getMessage());
//                }
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_l();
//                try {
//                    mediaPlayer.setDataSource(receivedMusicList.get(i).getMusicUrl());
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            mediaPlayer.start();
//                        }
//                    });
//                    mediaPlayer.prepareAsync();
//                } catch (IOException e) {
//                    Log.e("errrr", e.getMessage());
//                }
            }
        });


        mod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod1.setVisibility(View.GONE);
                mod2.setVisibility(View.VISIBLE);
                Sty=1;
            }
        });
        mod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod2.setVisibility(View.GONE);
                mod3.setVisibility(View.VISIBLE);
                Sty=2;
            }
        });
        mod3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod3.setVisibility(View.GONE);
                mod1.setVisibility(View.VISIBLE);
                Sty=3;
            }
        });
        imageView_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_music.this, Activity_Home.class);
//                if (receivedMusicList != null && !receivedMusicList.isEmpty()) {
//                    intent.putParcelableArrayListExtra("musicList", receivedMusicList);
//                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });

        imageView_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageView_like.isSelected()){
                    imageView_like.setSelected(false);
                    like_remove(imageView1);
                }
                else {
                    imageView_like.setSelected(true);
                    like_add(imageView1);
                }
            }
        });

        SeekBar seekBar = findViewById(R.id.music_progress);
        songDuration = mediaPlayer.getDuration();
        seekBar.setMax(songDuration);
        seekBar.setProgress(0);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentProgress = seekBar.getProgress();
                int newProgress = currentProgress + 10;
                if (newProgress <= seekBar.getMax()) {
                    seekBar.setProgress(newProgress);
                } else {
                    seekBar.setProgress(0);
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);


        textViewCurrentTime = findViewById(R.id.textViewCurrentTime);
        textViewTotalTime = findViewById(R.id.textViewTotalTime);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    textViewCurrentTime.setText(formatTime(progress));
//                    updateRecyclerView(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                mediaPlayer.start();
            }
        });
    }

    private class DownloadLyricsTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... urls) {
            String urlString = urls[0];
            List<String> lyricsList = new ArrayList<>();
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    lyricsList.add(line);
                }
                inputStream.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                Log.e("DownloadLyricsTask", "Error downloading lyrics: " + e.getMessage());
            }
            return lyricsList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            String lastLyricsLine = result.get(result.size() - 1);
            textViewTotalTime.setText(extractTime(lastLyricsLine));
            List<String> cleanedLyrics = removeTimeTags(result);
            updateLyricsView(cleanedLyrics);
        }

        // 去除每行歌词的时间标记部分
        private List<String> removeTimeTags(List<String> lyricsList) {
            List<String> cleanedLyrics = new ArrayList<>();
            for (String line : lyricsList) {
                // 使用正则表达式去除时间标记部分
                String cleanedLine = line.replaceAll("\\[\\d{2}:\\d{2}\\.\\d{2}\\]", "");
                cleanedLyrics.add(cleanedLine.trim()); // 去除前后空白字符
            }
            return cleanedLyrics;
        }
    }
    private String extractTime(String lyricsLine) {
        Pattern pattern = Pattern.compile("\\[(\\d{2}):(\\d{2})\\.");
        Matcher matcher = pattern.matcher(lyricsLine);
        if (matcher.find()) {
            String minutes = matcher.group(1);
            String seconds = matcher.group(2);
            return minutes + ":" + seconds;
        }
        return null;
    }


    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageUrl = strings[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        // 从 Palette 中获取主要颜色
                        Palette.Swatch dominantSwatch = palette.getDominantSwatch();
                        if (dominantSwatch != null) {
                            int color = dominantSwatch.getRgb();
                            ConstraintLayout mainLayout = findViewById(R.id.main_music);
                            mainLayout.setBackgroundColor(color);
                        }
                    }
                });
            }
        }
    }



    private void updateLyricsView(List<String> lyricsList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(imageView1.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LyricsAdapter(lyricsList);
        recyclerView.setAdapter(adapter);
    }

    public void setAnimator(ImageView imageView){
        rotateAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 360f);
        rotateAnimator.setDuration(30000);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimator);
    }

    @Override
    protected void onDestroy() {
//        stopService(serviceIntent);
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



    public void next_r(){
        if(i==receivedMusicList.size()-1){
            i=0;
        }
        else {
            i++;
        }
        Glide.with(imageView4.getContext())
                .load(receivedMusicList.get(i).getCoverUrl())  // 获取音乐数据对象的封面URL
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))  // 设置圆形转换
                .into(imageView1);
        textView_name.setText(receivedMusicList.get(i).getMusicName());
        textView_a.setText(receivedMusicList.get(i).getAuthor());
    }

    public void next_l(){
        if(i==receivedMusicList.size()-1){
            i=0;
        }
        else {
            i++;
        }
        Glide.with(imageView4.getContext())
                .load(receivedMusicList.get(i).getCoverUrl())  // 获取音乐数据对象的封面URL
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))  // 设置圆形转换
                .into(imageView1);
        textView_name.setText(receivedMusicList.get(i).getMusicName());
        textView_a.setText(receivedMusicList.get(i).getAuthor());
    }

    public void next_rec(){
        Glide.with(imageView4.getContext())
                .load(receivedMusicList.get(i).getCoverUrl())  // 获取音乐数据对象的封面URL
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))  // 设置圆形转换
                .into(imageView1);
        textView_name.setText(receivedMusicList.get(i).getMusicName());
        textView_a.setText(receivedMusicList.get(i).getAuthor());
    }

    private void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(imageView1.getContext());

        View dialogView = LayoutInflater.from(imageView1.getContext()).inflate(R.layout.layout_list, null);
        builder.setView(dialogView);

        builder.setTitle("当前播放:");
        builder.setTitle("收藏数:"+receivedMusicList.size());

        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view_options);

        LinearLayoutManager layoutManager = new LinearLayoutManager(imageView1.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter3 = new OptionsAdapter(receivedMusicList, new OptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 处理点击事件逻辑，例如移除某项数据
                receivedMusicList.remove(position);
                adapter3.notifyItemRemoved(position);
            }
        });
        recyclerView.setAdapter(adapter3);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();

    }
//    private void showOptionsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(textView_name.getContext());
//        builder.setTitle("当前播放:");
//
//        // 准备选项列表
//        List<String> items = new ArrayList<>();
//
//
//        // 将音乐列表中的歌曲名添加到选项列表中
//        for (MusicData_Activity musicDataActivity : receivedMusicList) {
//            items.add(musicDataActivity.getMusicName());
//        }
//
//        // 转换成数组以便传递给对话框
//        final CharSequence[] itemsArray = items.toArray(new CharSequence[items.size()]);
//
//        // 设置选项列表
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
////                        String selectedMusicName = items.get(i - 3); // 选项数组前面有三个固定选项
////                        handleMusicOptionSelected(selectedMusicName);
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

    // 处理音乐选项被选中的方法
    private void handleMusicOptionSelected(String musicName) {

    }

    private void updateRecyclerView(int progress) {
        // 根据进度查找 RecyclerView 中对应的歌词项
        // 假设 lyricsAdapter 已经实现了方法来更新当前项的高亮状态
        adapter.updateCurrentItem(progress);
    }

    // 格式化时间为 mm:ss 格式
    private String formatTime(int milliseconds) {
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
    public void like_add(ImageView imageView){
        // 点击放大动画
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 1.2f, 1.0f);
        scaleUpX.setDuration(1000);
        scaleUpY.setDuration(1000);
        ObjectAnimator rotateY = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 360f);
        rotateY.setDuration(1000);
        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.play(scaleUpX).with(scaleUpY).with(rotateY);
        scaleUp.start();
    }
    public void like_remove(ImageView imageView){
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 0.8f, 1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 0.8f, 1.0f);
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);
        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);
        scaleDown.start();

    }
}