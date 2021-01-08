package nicokla.com.musicos.algolia.user

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algolia.instantsearch.helper.android.highlighting.toSpannedString
import nicokla.com.musicos.databinding.SongItemWithAuthorBinding
import nicokla.com.musicos.databinding.UserItemBinding

//import kotlinx.android.synthetic.main.user_item.view.*

class UserViewHolder(private val binding: UserItemBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.userName.text = user.highlightedName?.toSpannedString() ?: user.name
    }
}