package nicokla.com.musicos.algolia.song

import com.algolia.instantsearch.core.highlighting.HighlightedString
import com.algolia.instantsearch.helper.highlighting.Highlightable
import com.algolia.search.model.Attribute
import com.algolia.search.model.ObjectID
import com.algolia.search.model.indexing.Indexable
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.JsonObject


//public float duration;
//public long datetime;
//public String ownerName;
//public String imageUrl;
//public String videoID;
//public String originalID;
//public String ownerID;
//public String title;
//public String objectID;

@Serializable
data class Song(
        val title: String, // name
        val ownerName: String, // name
        val imageUrl: String, // name
        val videoID: String,
        override val objectID: ObjectID,
        override val _highlightResult: JsonObject?
) : Indexable, Highlightable {

    @Transient
    public val highlightedName: HighlightedString?
        get() = getHighlight(Attribute("title"))
}