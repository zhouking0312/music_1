package com.example.myapplication.Bean

data class DataBean(
    val records: MutableList<RecordBean>,
    val total: Int,
    val size: Int,
    val current: Int,
    val pages: Int
)
