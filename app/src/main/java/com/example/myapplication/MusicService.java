package com.example.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;


import java.io.IOException;

public class MusicService extends Service implements AudioManager.OnAudioFocusChangeListener {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            try {
                mediaPlayer.setDataSource(intent.getStringExtra("musicUrl"));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        // 释放音频焦点
        audioManager.abandonAudioFocus(this);
        super.onDestroy();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
                // 长时间失去焦点，暂停播放并释放资源
                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
                    mediaPlayer.start();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // 短暂失去焦点，暂停播放
                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
                    mediaPlayer.start();
                }
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                // 获得焦点，继续播放
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
        }
    }
}
