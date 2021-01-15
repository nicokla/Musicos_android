package com.nicokla.musicos2.algolia.song

//import com.algolia.instantsearch.guides.R
//import kotlinx.android.synthetic.main.fragment_song.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxViewAppCompat
import com.algolia.instantsearch.helper.android.searchbox.connectView
import com.nicokla.musicos2.MainAndCo.GlobalVars
import com.nicokla.musicos2.databinding.FragmentSongBinding
import com.nicokla.musicos2.navigation.SearchFragmentDirections

interface CellClickListener {
    fun onCellClickListener(song: Song)
}

class SearchSongsFragment : Fragment(), CellClickListener {
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
//        Toast.makeText(context,"Cell clicked: " + song.title, Toast.LENGTH_SHORT).show()
        GlobalVars.getInstance().songFirestore.set(
            song.duration, song.datetime, song.ownerName, song.imageUrl,
            song.videoID, song.originalID, song.ownerID,
            song.title, song.objectID.toString()
        )
        view?.findNavController()?.navigate(
            SearchFragmentDirections.actionSearchFragmentToPlayerFragment(song.videoID, song.objectID.toString())
        )
    }

}