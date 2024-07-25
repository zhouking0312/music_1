package com.example.myapplication.Bean

data class RecordBean(
    val moduleConfigId: Int,
    val moduleName: String,
    val style: Int,
    val musicInfoList: MutableList<MusicInfoBean>
)
