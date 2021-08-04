package com.example.musicdemo.data.source.local

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import com.example.musicdemo.data.model.Song
import com.example.musicdemo.data.source.SongDataSource

class SongLocalData(private val context: Context?, private val songDataSource: SongDataSource) {

    private var listSong = ArrayList<Song>()

    fun getLocalSong() {
        val resolver: ContentResolver? = context?.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val query = resolver?.query(uri, null, null, null, null)
            ?: throw IllegalStateException("System returned null.")

        query.use { cursor ->
            while (cursor.moveToNext()) {
                listSong.add(Song(cursor))
            }
            songDataSource.getLocalSongs(listSong)
        }
    }
}

