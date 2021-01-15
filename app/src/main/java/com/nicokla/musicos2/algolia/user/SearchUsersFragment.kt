package com.nicokla.musicos2.algolia.user

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
//import com.algolia.instantsearch.guides.R
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxViewAppCompat
import com.algolia.instantsearch.helper.android.searchbox.connectView
import com.algolia.instantsearch.helper.android.stats.StatsTextView
import com.algolia.instantsearch.helper.stats.StatsPresenterImpl
import com.algolia.instantsearch.helper.stats.connectView
import com.nicokla.musicos2.MainAndCo.GlobalVars
//import kotlinx.android.synthetic.main.fragment_user.*
import com.nicokla.musicos2.databinding.FragmentUserBinding
import com.nicokla.musicos2.navigation.SearchFragmentDirections

interface CellClickListener {
    fun onCellClickListener(user: User)
}

class SearchUsersFragment : Fragment(), CellClickListener {

    private val connection = ConnectionHandler()
//    private lateinit var binding: FragmentUserBinding
    private var _binding: FragmentUserBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
//        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        val adapterUser = UserAdapter(this)
        viewModel.users.observe(
                viewLifecycleOwner,
                Observer { hits -> adapterUser.submitList(hits) })

        binding.userList.let {
            it.itemAnimator = null
            it.adapter = adapterUser
            it.layoutManager = LinearLayoutManager(requireContext())
            it.autoScrollToStart(adapterUser)
        }

        val searchBoxView = SearchBoxViewAppCompat(binding.searchView)
        connection += viewModel.searchBox.connectView(searchBoxView)

        val statsView = StatsTextView(binding.stats)
        connection += viewModel.stats.connectView(statsView, StatsPresenterImpl())

//        filters.setOnClickListener {  }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connection.clear()
        _binding = null
    }

    override fun onCellClickListener(user: User) {
//        Toast.makeText(context,"Cell clicked: " + user.name, Toast.LENGTH_SHORT).show()
        GlobalVars.getInstance().user.name = user.name;
        GlobalVars.getInstance().user.objectID = user.objectID.toString();
        view?.findNavController()?.navigate(
                SearchFragmentDirections.actionSearchFragmentToOtherUserFragment(user.objectID.toString())
        )
    }

}