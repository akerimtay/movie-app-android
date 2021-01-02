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
import com.akerimtay.movieapp.databinding.FragmentDetailsBinding
import com.akerimtay.movieapp.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModel()
    private val navArgs: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = navArgs.movieId
        viewModel.setMovieId(movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViews()
        observeViewModel()
    }

    private fun observeViews() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun observeViewModel() {
        viewModel.movie.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    binding.progressView.isVisible = true
                }
                Resource.Status.SUCCESS -> {
                    binding.progressView.isVisible = false
                }
                Resource.Status.ERROR -> {
                    binding.progressView.isVisible = false
                    showToast(resource.message)
                }
            }
        }
    }
}