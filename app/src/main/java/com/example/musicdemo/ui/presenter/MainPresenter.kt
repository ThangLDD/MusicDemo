package com.example.musicdemo.ui.presenter

import android.content.Context
import com.example.musicdemo.ui.ViewListener
import com.example.musicdemo.data.model.Song
import com.example.musicdemo.data.source.SongDataSource
import com.example.musicdemo.data.source.local.SongLocalData

class MainPresenter(private var viewListener: ViewListener, context: Context?) : SongDataSource {

    private var songLocalData: SongLocalData = SongLocalData(context, this)

    fun getData() {
        songLocalData.getLocalSong()
    }

    override fun getLocalSongs(listSong: MutableList<Song>) {
        viewListener.displayView(listSong)
    }
}

