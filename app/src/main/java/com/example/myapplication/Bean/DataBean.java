package com.example.myapplication.Bean;

import java.util.List;

public class DataBean {
    public List<RecordBean> getRecordsBean() {
        return records;
    }

    public void setRecordsBean(List<RecordBean> records) {
        this.records = records;
    }

    private List<RecordBean> records;
    private int total;
    private int size;
    private int current;
    private int pages;
}
