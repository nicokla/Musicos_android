package com.nicokla.musicos2.algolia.song

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algolia.instantsearch.helper.android.highlighting.toSpannedString
import com.bumptech.glide.Glide
import com.nicokla.musicos2.databinding.FragmentSongBinding
//import kotlinx.android.synthetic.main.song_item_with_author.view.*
import com.nicokla.musicos2.databinding.SongItemWithAuthorBinding
import com.nicokla.musicos2.databinding.UserItemBinding

class SongViewHolder(private val binding: SongItemWithAuthorBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(song: Song) {
        binding.videoTitle.text = song.highlightedName?.toSpannedString() ?: song.title
        binding.videoDescription.text = song.ownerName
        Glide
            .with(binding.root.context)
            .load(song.imageUrl)
            .into(binding.videoThumbnail)
    }
}