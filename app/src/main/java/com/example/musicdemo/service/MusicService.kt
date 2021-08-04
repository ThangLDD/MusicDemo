package com.example.musicdemo.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.musicdemo.ui.ViewListener
import com.example.musicdemo.ui.presenter.MainPresenter
import com.example.musicdemo.data.model.Song
import com.example.musicdemo.data.source.local.SongHandler

class MusicService : Service(), SongHandler, ViewListener {

    private var mediaPlayer: MediaPlayer? = null
    private var listSong: MutableList<Song> = mutableListOf()
    private var position = 0
    private var song: Song? = null

    override fun onCreate() {
        super.onCreate()
        MainPresenter(this, this).getData()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startMediaPlayer()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = MusicBinder()

    fun createMediaPlayer(pos: Int) {
        position = pos
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(listSong[pos].data)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    override fun resumeMediaPlayer() {
        mediaPlayer?.let {
            if (it.isPlaying) it.pause()
            else it.start()
        }
    }

    override fun createMediaPlayer(path: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(path)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    override fun stopMediaPlayer() {
        mediaPlayer?.let {
            it.stop()
            it.release()
        }
        mediaPlayer = null
    }

    override fun startMediaPlayer() {
        mediaPlayer?.let {
            stopMediaPlayer()
        }
        createMediaPlayer(listSong[position].data)
    }

    fun getMediaPlayer(): MediaPlayer? = mediaPlayer
    fun getListSong(): MutableList<Song> = listSong
    fun getTitle(): String = listSong[position].title
    fun getArtist(): String = listSong[position].artist

    override fun getCurrentPositionMediaPlayer(): Int? = mediaPlayer?.currentPosition

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun displayView(songs: MutableList<Song>) {
        listSong.addAll(songs)
        song = songs[position]
    }
}

