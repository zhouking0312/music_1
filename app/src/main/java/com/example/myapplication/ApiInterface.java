package com.example.myapplication;

import com.example.myapplication.Bean.ResponseBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("music/homePage")
    Call<ResponseBean> getString(
            @Query("current") int current,
            @Query("size") int size
    );
}
