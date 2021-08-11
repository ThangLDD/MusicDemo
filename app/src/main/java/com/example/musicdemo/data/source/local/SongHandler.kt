package com.example.musicdemo.data.source.local

interface SongHandler {
    fun createMediaPlayer(path: String)
    fun startMediaPlayer()
    fun resumeMediaPlayer()
    fun stopMediaPlayer()
    fun getCurrentPositionMediaPlayer(): Int?
}

