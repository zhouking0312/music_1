package com.example.myapplication.Bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicData_Activity implements Parcelable {
    private int id;
    private String musicName;
    private String author;
    private String coverUrl;
    private String musicUrl;
    private String lyricUrl;

    // 构造方法
    public MusicData_Activity(MusicInfoBean musicInfoBean) {
        this.musicName=musicInfoBean.getMusicName();
        this.musicUrl=musicInfoBean.getMusicUrl();
        this.author=musicInfoBean.getAuthor();
        this.coverUrl=musicInfoBean.getCoverUrl();
        this.lyricUrl=musicInfoBean.getLyricUrl();
        // 默认构造方法
    }

    // Parcelable 构造方法
    protected MusicData_Activity(Parcel in) {
        id = in.readInt();
        musicName = in.readString();
        author = in.readString();
        coverUrl = in.readString();
        musicUrl = in.readString();
        lyricUrl = in.readString();
    }

    // 实现 Parcelable.Creator 接口
    public static final Creator<MusicData_Activity> CREATOR = new Creator<MusicData_Activity>() {
        @Override
        public MusicData_Activity createFromParcel(Parcel in) {
            return new MusicData_Activity(in);
        }

        @Override
        public MusicData_Activity[] newArray(int size) {
            return new MusicData_Activity[size];
        }
    };

    // 写入数据到 Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(musicName);
        dest.writeString(author);
        dest.writeString(coverUrl);
        dest.writeString(musicUrl);
        dest.writeString(lyricUrl);
    }

    // 描述对象的特殊对象类型，默认返回0即可
    @Override
    public int describeContents() {
        return 0;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getLyricUrl() {
        return lyricUrl;
    }

    public void setLyricUrl(String lyricUrl) {
        this.lyricUrl = lyricUrl;
    }
}
