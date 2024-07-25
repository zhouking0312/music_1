package com.example.myapplication

import com.example.myapplication.Bean.ResponseBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("music/homePage")
    fun getString(
        @Query("current") current: Int,
        @Query("size") size: Int
    ): Call<ResponseBean>
}