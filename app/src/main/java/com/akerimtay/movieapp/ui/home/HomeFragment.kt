package com.akerimtay.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.databinding.FragmentHomeBinding
import com.akerimtay.movieapp.extensions.dpToPx
import com.akerimtay.movieapp.extensions.showToast
import com.akerimtay.movieapp.utils.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        val spacing = requireContext().dpToPx(24)
        val itemDecoration = SpaceItemDecoration(spacing, SpaceItemDecoration.HORIZONTAL)

        // Top rated movies
        topRatedMoviesAdapter = MoviesAdapter { openMovieDetailsPage(it) }
        binding.topRatedRecycler.apply {
            adapter = topRatedMoviesAdapter
            addItemDecoration(itemDecoration)
        }

        // Now playing movies
        nowPlayingMoviesAdapter = MoviesAdapter { openMovieDetailsPage(it) }
        binding.nowPlayingRecycler.apply {
            adapter = nowPlayingMoviesAdapter
            addItemDecoration(itemDecoration)
        }

        // Popular movies
        popularMoviesAdapter = MoviesAdapter { openMovieDetailsPage(it) }
        binding.popularRecycler.apply {
            adapter = popularMoviesAdapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun observeViewModel() {
        viewModel.topRatedMovies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    binding.progressTopRated.isVisible = true
                }
                Resource.Status.SUCCESS -> {
                    binding.progressTopRated.isVisible = false
                    resource.data?.let { topRatedMoviesAdapter.setItems(it) }
                }
                Resource.Status.ERROR -> {
                    binding.progressTopRated.isVisible = false
                    showToast(resource.message)
                }
            }
        }
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    binding.progressNowPlaying.isVisible = true
                }
                Resource.Status.SUCCESS -> {
                    binding.progressNowPlaying.isVisible = false
                    resource.data?.let { nowPlayingMoviesAdapter.setItems(it) }
                }
                Resource.Status.ERROR -> {
                    binding.progressNowPlaying.isVisible = false
                    showToast(resource.message)
                }
            }
        }
        viewModel.popularMovies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    binding.progressPopular.isVisible = true
                }
                Resource.Status.SUCCESS -> {
                    binding.progressPopular.isVisible = false
                    resource.data?.let { popularMoviesAdapter.setItems(it) }
                }
                Resource.Status.ERROR -> {
                    binding.progressPopular.isVisible = false
                    showToast(resource.message)
                }
            }
        }
    }

    private fun openMovieDetailsPage(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie.id)
        findNavController().navigate(action)
    }
}