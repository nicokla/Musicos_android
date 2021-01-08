package nicokla.com.musicos.algolia.song

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.SearcherSingleIndexDataSource
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxConnectorPagedList
import com.algolia.instantsearch.helper.searcher.SearcherSingleIndex
import com.algolia.instantsearch.helper.stats.StatsConnector
import com.algolia.search.client.ClientSearch
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import io.ktor.client.features.logging.LogLevel
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class MyViewModelSongs : ViewModel() {

    val client = ClientSearch(
//            ApplicationID("latency"),
//            APIKey("1f6fd3a6fb973cb08419fe7d288fa4db"),
            ApplicationID("SKJIA8T5Z2"),
            APIKey("5e2190935369d22de34d9ff049391343"),
            LogLevel.ALL
    )
    val index = client.initIndex(IndexName("songs")) // bestbuy_promo
    val searcher = SearcherSingleIndex(index)

    val dataSourceFactory = SearcherSingleIndexDataSource.Factory(searcher) {
        it.deserialize(Song.serializer())
    }

//    hit ->
//    Song(
//    hit.json.getValue("title").jsonPrimitive.content,
//    hit.json.getValue("ownerName").jsonPrimitive.content,
//    hit.json.getValue("imageUrl").jsonPrimitive.content,
//    hit.json.getValue("objectID").jsonPrimitive.content,
//    hit.json["_highlightResult"]?.jsonObject
//    )

    val pagedListConfig =
            PagedList.Config.Builder().setPageSize(50).setEnablePlaceholders(false).build()
    val songs: LiveData<PagedList<Song>> =
            LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    val searchBox = SearchBoxConnectorPagedList(searcher, listOf(songs))
    val stats = StatsConnector(searcher)

    val connection = ConnectionHandler()

    init {
        connection += searchBox
        connection += stats
    }

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.clear()
    }
}