package com.example.myapplication.Bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MyItem implements MultiItemEntity {
    public int itemType;
//    private List<MusicInfoBean> musicInfoList = new ArrayList<>();
    public static final int ONE=1;
    public static final int TWO=2;
    public static final int THREE=3;
    public static final int FOUR=4;
    private String musicName;
    private String author;
    private String coverUrl;

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public MyItem(int itemType, String musicName,String coverUrl,String author) {
        this.itemType = itemType;
        this.coverUrl = coverUrl;
        this.author = author;
        this.musicName = musicName;
    }



    @Override
    public int getItemType() {
        return itemType;
    }

}


