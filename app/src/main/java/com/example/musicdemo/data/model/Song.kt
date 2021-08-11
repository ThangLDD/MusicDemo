@file:Suppress("DEPRECATION")
package com.example.musicdemo.data.model

import android.database.Cursor
import android.provider.MediaStore

data class Song(val data: String, val title: String, val artist: String) {
    constructor(cursor: Cursor) : this(
        data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
        title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
        artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
    )
}

