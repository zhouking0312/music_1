package com.example.myapplication.Bean



interface DataChangeListener {
    fun onDataChanged()
}

object SingletonMusicInfo {
    private val musicList = mutableListOf<MusicInfoBean>()

    private val listeners = mutableListOf<DataChangeListener>()

    fun addMusic(musicInfo: MusicInfoBean,i: Int?) {
        val existingIndex = musicList.indexOfFirst { it.id == musicInfo.id }
        if (existingIndex != -1) {
            val existingMusic = musicList.removeAt(existingIndex)
            musicList.add(0, existingMusic)
        } else {
            if (i == null) {
                musicList.add(musicInfo)
            } else {
                musicList.add(i, musicInfo)
            }
        }
        notifyDataChanged()
    }
    fun removeMusic(musicId: Int) {
        val iterator = musicList.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().id == musicId) {
                iterator.remove()
                break
            }
        }
        notifyDataChanged()
    }

    fun getAllMusic(): MutableList<MusicInfoBean> {
        return musicList
    }

    fun clearAllMusic() {
        musicList.clear()
        notifyDataChanged()
    }

    fun registerListener(listener: DataChangeListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: DataChangeListener) {
        listeners.remove(listener)
    }

    private fun notifyDataChanged() {
        listeners.forEach { it.onDataChanged() }
    }
}