package com.example.myapplication.Bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class MyData(override var itemType: Int,var musicInfoList: MutableList<MusicInfoBean>) :
    MultiItemEntity {
    companion object {
        const val ONE: Int = 1
        const val TWO: Int = 2
        const val THREE: Int = 3
        const val FOUR: Int = 4
    }
}


