package com.example.myapplication.Bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MyItem2 implements MultiItemEntity {
    public int itemType;
    //    private List<MusicInfoBean> musicInfoList = new ArrayList<>();
    public static final int ONE=1;
    public static final int TWO=2;
    public static final int THREE=3;
    public static final int FOUR=4;
    private List<MusicInfoBean> musicInfoList;

    public List<MusicInfoBean> getMusicInfoList() {
        return musicInfoList;
    }

    public void setMusicInfoList(List<MusicInfoBean> musicInfoList) {
        this.musicInfoList = musicInfoList;
    }

    public MyItem2(int itemType, List<MusicInfoBean> musicInfoList) {
        this.itemType = itemType;
        this.musicInfoList=musicInfoList;
    }



    @Override
    public int getItemType() {
        return itemType;
    }

}



