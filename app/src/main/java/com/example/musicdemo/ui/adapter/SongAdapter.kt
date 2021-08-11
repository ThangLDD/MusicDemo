package com.example.musicdemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicdemo.data.model.Song
import com.example.musicdemo.databinding.ItemSongBinding

class SongAdapter(
    private var listSong: MutableList<Song>,
    private var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val songBinding =
            ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(songBinding.root, songBinding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = listSong[position]
        holder.bindView(song)
    }

    override fun getItemCount() = listSong.size

    class ViewHolder(
        view: View,
        private val binding: ItemSongBinding,
        onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                onItemClickListener.onCLickItem(adapterPosition)
            }
        }

        fun bindView(song: Song) {
            binding.let {
                it.textViewTitleItem.text = song.title
                it.textViewArtistItem.text = song.artist
            }
        }
    }
}


