package com.akerimtay.movieapp.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.datasource.paging.NetworkState
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.databinding.FragmentSearchBinding
import com.akerimtay.movieapp.extensions.dpToPx
import com.akerimtay.movieapp.extensions.hideKeyboard
import com.akerimtay.movieapp.extensions.showToast
import com.akerimtay.movieapp.utils.SpaceItemDecoration
import com.akerimtay.movieapp.utils.debounce
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SearchFragment : Fragment(), MoviePagedAdapter.OnMovieClickListener {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var binding: FragmentSearchBinding
    private lateinit var moviesAdapter: MoviePagedAdapter

    private var isSwipeRefreshEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerView()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CODE_VOICE_RECOGNIZER -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val voiceQuery = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    binding.searchView.setQuery(voiceQuery?.firstOrNull(), true)
                }
            }
        }
    }

    //////////////////// OnMovieClickListener ////////////////////
    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        binding.swipeLayout.isRefreshing = size == 0 && networkState == NetworkState.RUNNING
    }

    override fun onClickRetry() {
        viewModel.refreshFailedRequest()
    }

    override fun onMovieClick(movie: Movie) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movie.id)
        findNavController().navigate(action)
    }
    ///////////////////////////// end ////////////////////////////

    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) { moviesAdapter.submitList(it) }
        viewModel.networkState.observe(viewLifecycleOwner) { moviesAdapter.updateNetworkState(it) }
    }

    private fun setupUI() {
        binding.swipeLayout.isEnabled = isSwipeRefreshEnabled
        binding.arrowBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.imgVoiceSearch.setOnClickListener { openVoice() }
        setupSearchView()
    }

    private fun setupRecyclerView() {
        val spacing = requireContext().dpToPx(24)
        val itemDecoration = SpaceItemDecoration(spacing, SpaceItemDecoration.VERTICAL)

        moviesAdapter = MoviePagedAdapter(this)
        binding.moviesRecycler.apply {
            adapter = moviesAdapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun setupSearchView() {
        binding.searchView.onActionViewExpanded()

        val scope = viewModel.viewModelScope
        val onTextChange = debounce(DELAY_TIME_MILLIS, scope, viewModel::searchMovie)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                query.let(onTextChange)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return true
            }
        })
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openVoice() {
        val voiceIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        voiceIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.say_movie_name))

        try {
            requireActivity().packageManager?.let { voiceIntent.resolveActivity(it) }
            startActivityForResult(voiceIntent, CODE_VOICE_RECOGNIZER)
        } catch (e: Exception) {
            showToast(e.localizedMessage)
        }
    }

    companion object {
        private const val CODE_VOICE_RECOGNIZER = 10
        private const val DELAY_TIME_MILLIS = 300L
    }
}