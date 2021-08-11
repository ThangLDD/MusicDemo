package com.example.musicdemo.ui

import com.example.musicdemo.data.model.Song

interface ViewListener {
    fun displayView(songs: MutableList<Song>)
}

