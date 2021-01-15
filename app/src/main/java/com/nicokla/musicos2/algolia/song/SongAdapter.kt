package com.nicokla.musicos2.algolia.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
//import com.algolia.instantsearch.guides.R
//import com.algolia.instantsearch.guides.gettingstarted.Song
//import com.algolia.instantsearch.guides.gettingstarted.SongViewHolder
import com.nicokla.musicos2.R
import com.nicokla.musicos2.databinding.SongItemWithAuthorBinding


class SongAdapter(private val cellClickListener: CellClickListener)
    : PagedListAdapter<Song, SongViewHolder>(SongAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    SongViewHolder(
        // Inflate generated item binding class
        SongItemWithAuthorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)

        if (song != null) {
            holder.bind(song)
            holder.itemView.setOnClickListener {
                cellClickListener.onCellClickListener(song)
            }
        }
    }

    companion object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(
                oldItem: Song,
                newItem: Song
        ): Boolean {
            return oldItem::class == newItem::class
        }

        override fun areContentsTheSame(
                oldItem: Song,
                newItem: Song
        ): Boolean {
            return oldItem.objectID == newItem.objectID
        }
    }
}