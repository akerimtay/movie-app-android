package com.akerimtay.movieapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.MovieFull
import com.akerimtay.movieapp.databinding.FragmentDetailsBinding
import com.akerimtay.movieapp.extensions.dpToPx
import com.akerimtay.movieapp.extensions.loadOriginalImage
import com.akerimtay.movieapp.extensions.showToast
import com.akerimtay.movieapp.ui.home.MoviesAdapter
import com.akerimtay.movieapp.utils.SpaceItemDecoration
import com.akerimtay.movieapp.utils.getBrowserIntent
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModel()
    private val navArgs: DetailsFragmentArgs by navArgs()

    private lateinit var creditsAdapter: CreditsAdapter
    private lateinit var similarAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = navArgs.movieId
        viewModel.setMovieId(movieId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupUI()
        observeViewModel()
    }

    private fun setupRecyclerViews() {
        val spacing = requireContext().dpToPx(24)
        val itemDecoration = SpaceItemDecoration(spacing, SpaceItemDecoration.HORIZONTAL)

        // Credits
        creditsAdapter = CreditsAdapter()
        binding.contentDetails.creditRecycler.apply {
            adapter = creditsAdapter
            addItemDecoration(itemDecoration)
        }

        // Similar movies
        similarAdapter = MoviesAdapter {
            //TODO Handle click
            showToast(R.string.on_dev)
        }
        binding.contentDetails.similarRecycler.apply {
            adapter = similarAdapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun setupUI() {
        with(binding) {
            updateBannerShimmer(isLoading = true)
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun observeViewModel() {
        viewModel.movie.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    updateContentShimmer(isLoading = true)
                }
                Resource.Status.SUCCESS -> {
                    resource.data?.let { initMovie(it) }
                    updateContentShimmer(isLoading = false)
                }
                Resource.Status.ERROR -> {
                    updateContentShimmer(isLoading = false)
                    showToast(resource.message)
                }
            }
        }
        viewModel.credits.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    resource.data?.getCastAndCrew()?.let { creditsAdapter.setItems(it) }
                }
                Resource.Status.ERROR -> {
                    showToast(resource.message)
                }
            }
        }
        viewModel.similarMovies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    resource.data?.movies?.let { similarAdapter.setItems(it) }
                }
                Resource.Status.ERROR -> {
                    showToast(resource.message)
                }
            }
        }
    }

    private fun initMovie(movie: MovieFull) {
        with(binding) {
            contentDetails.layoutContent.isVisible = true

            imgBanner.loadOriginalImage(
                path = movie.backdropPath,
                onFailed = {
                    imgBanner.setBackgroundResource(R.drawable.placeholder_poster)
                    updateBannerShimmer(isLoading = false)
                },
                onReady = { updateBannerShimmer(isLoading = false) }
            )
        }

        binding.contentDetails.btnOfficialPage.setOnClickListener {
            startActivity(getBrowserIntent(movie.homepage))
        }
    }

    private fun updateBannerShimmer(isLoading: Boolean) {
        with(binding) {
            bannerShimmerLayout.isVisible = isLoading
            if (isLoading) {
                bannerShimmerLayout.startShimmer()
            } else {
                bannerShimmerLayout.stopShimmer()
            }
        }
    }

    private fun updateContentShimmer(isLoading: Boolean) {
        with(binding.contentDetails) {
            contentShimmerLayout.isVisible = isLoading
            if (isLoading) {
                contentShimmerLayout.startShimmer()
            } else {
                contentShimmerLayout.stopShimmer()
            }
        }
    }
}