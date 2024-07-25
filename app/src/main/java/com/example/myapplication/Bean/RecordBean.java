package com.example.myapplication.Bean;



import java.util.List;

public class RecordBean {
    private int moduleConfigId;
    private String moduleName;
    private int style;
    private List<MusicInfoBean> musicInfoList;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public List<MusicInfoBean> getMusicInfoList() {
        return musicInfoList;
    }

    public void setMusicInfoList(List<MusicInfoBean> musicInfoList) {
        this.musicInfoList = musicInfoList;
    }
}
