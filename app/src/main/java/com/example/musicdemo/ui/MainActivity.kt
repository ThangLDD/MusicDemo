package com.example.musicdemo.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicdemo.R
import com.example.musicdemo.data.model.Song
import com.example.musicdemo.service.MusicService
import com.example.musicdemo.ui.adapter.OnItemClickListener
import com.example.musicdemo.ui.adapter.SongAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var musicService: MusicService
    private var isBound = false
    private var songAdapter: SongAdapter? = null
    private var songs: MutableList<Song> = mutableListOf()

    private val connect = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val serviceBinder = p1 as MusicService.MusicBinder
            musicService = serviceBinder.getService()
            isBound = true

            setView()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Intent(this, MusicService::class.java).also { intent ->
            bindService(intent, connect, Context.BIND_AUTO_CREATE)
        }
        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_DENIED
        ) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setView() {
        assignViews(buttonControl, tv_SongTitle, tv_Artist, recyclerViewSong)
        songs.clear()
        songs.addAll(musicService.getListSong())
        songAdapter = SongAdapter(songs, this)
        recyclerView = recyclerViewSong
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = songAdapter
        recyclerView?.setHasFixedSize(true)
    }

    companion object {
        private const val REQUEST_CODE = 1
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonControl -> playPause()
        }
    }

    private fun playPause() {
        if (musicService.getMediaPlayer() != null) {
            musicService.resumeMediaPlayer()
            if (musicService.getMediaPlayer()?.isPlaying!!) {
                buttonControl.setImageResource(R.drawable.ic_baseline_pause_24)
            } else {
                buttonControl.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        } else {
            musicService.startMediaPlayer()
            buttonControl.setImageResource(R.drawable.ic_baseline_pause_24)
        }
        updatePlayer()

    }


    private fun updatePlayer() {
        musicService.getMediaPlayer().let {
            tv_SongTitle.text = musicService.getTitle()
            tv_Artist.text = musicService.getArtist()
        }
    }

    private fun View.OnClickListener.assignViews(vararg view: View?) {
        view.forEach {
            it?.setOnClickListener(this)
        }
    }

    override fun onCLickItem(position: Int) {
        musicService.stopMediaPlayer()
        musicService.createMediaPlayer(position)
        updatePlayer()
        buttonControl.setImageResource(R.drawable.ic_baseline_pause_24)
    }

    override fun onDestroy() {
        if (isBound) {
            unbindService(connect)
            isBound = false
        }
        super.onDestroy()
    }
}



