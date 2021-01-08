package nicokla.com.musicos.algolia

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.algolia.instantsearch.helper.android.highlighting.toSpannedString
//import kotlinx.android.synthetic.main.list_item_product.view.*
import kotlinx.android.synthetic.main.list_item_small.view.*
import nicokla.com.musicos.algolia.Product


class ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(product: Product) {
        view.itemName.text = product.highlightedName?.toSpannedString() ?: product.name
    }
}