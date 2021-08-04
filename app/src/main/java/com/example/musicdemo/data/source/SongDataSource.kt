package com.example.musicdemo.data.source

import com.example.musicdemo.data.model.Song

interface SongDataSource {
    fun getLocalSongs(listSong: MutableList<Song>)
}

