package com.example.musicdemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicdemo.R
import com.example.musicdemo.data.model.Song
import kotlinx.android.synthetic.main.item_song.view.*

class SongAdapter(
    private var listSong: MutableList<Song>, private var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return ViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = listSong[position]
        holder.title.text = song.title
        holder.artist.text = song.artist
    }

    override fun getItemCount(): Int = listSong.size

    inner class ViewHolder(view: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        var title: TextView = view.textViewSongTitle
        var artist: TextView = view.textViewArtistName

        init {
            view.setOnClickListener {
                onItemClickListener.onCLickItem(adapterPosition)
            }
        }
    }
}


