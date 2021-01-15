package com.nicokla.musicos2.algolia.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
//import com.algolia.instantsearch.guides.R
//import com.algolia.instantsearch.guides.gettingstarted.User
//import com.algolia.instantsearch.guides.gettingstarted.UserViewHolder
import com.nicokla.musicos2.R
import com.nicokla.musicos2.algolia.song.SongViewHolder
import com.nicokla.musicos2.databinding.SongItemWithAuthorBinding
import com.nicokla.musicos2.databinding.UserItemBinding


class UserAdapter(private val cellClickListener: CellClickListener)
    : PagedListAdapter<User, UserViewHolder>(UserAdapter) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    UserViewHolder(
            // Inflate generated item binding class
            UserItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)

        if (user != null) {
            holder.bind(user)
            holder.itemView.setOnClickListener {
                cellClickListener.onCellClickListener(user)
            }
        }
    }

    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
                oldItem: User,
                newItem: User
        ): Boolean {
            return oldItem::class == newItem::class
        }

        override fun areContentsTheSame(
                oldItem: User,
                newItem: User
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
}