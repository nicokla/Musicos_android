package nicokla.com.musicos.algolia.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.algolia.instantsearch.core.connection.ConnectionHandler
//import com.algolia.instantsearch.guides.R
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxViewAppCompat
import com.algolia.instantsearch.helper.android.searchbox.connectView
import com.algolia.instantsearch.helper.android.stats.StatsTextView
import com.algolia.instantsearch.helper.stats.StatsPresenterImpl
import com.algolia.instantsearch.helper.stats.connectView
//import kotlinx.android.synthetic.main.fragment_song.*
import nicokla.com.musicos.R
import nicokla.com.musicos.algolia.song.SongAdapter
import nicokla.com.musicos.algolia.user.MyViewModel
import nicokla.com.musicos.databinding.FragmentSongBinding
import nicokla.com.musicos.databinding.FragmentUserBinding

interface CellClickListener {
    fun onCellClickListener(song: Song)
}

class SongFragment : Fragment(), CellClickListener {
    private val connection = ConnectionHandler()

//    private lateinit var binding: FragmentSongBinding
    private var _binding: FragmentSongBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
//        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MyViewModelSongs::class.java]
//        val view = binding.root

        val adapterSong = SongAdapter(this)
        viewModel.songs.observe(
                viewLifecycleOwner,
                Observer { hits -> adapterSong.submitList(hits) })

        binding.songList.let {
            it.itemAnimator = null
            it.adapter = adapterSong
            it.layoutManager = LinearLayoutManager(requireContext())
            it.autoScrollToStart(adapterSong)
        }

        val searchBoxView = SearchBoxViewAppCompat(binding.searchView)
        connection += viewModel.searchBox.connectView(searchBoxView)

//        val statsView = StatsTextView(stats)
//        connection += viewModel.stats.connectView(statsView, StatsPresenterImpl())

//        filters.setOnClickListener {  }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connection.clear()
    }

    override fun onCellClickListener(song: Song) {
        Toast.makeText(context,"Cell clicked: " + song.title, Toast.LENGTH_SHORT).show()
    }

}