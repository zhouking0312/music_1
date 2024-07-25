package com.example.myapplication.Bean;

public class ResponseBean {
    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getDataBean() {
        return data;
    }

    public void setDataBean(DataBean data) {
        this.data = data;
    }
}
