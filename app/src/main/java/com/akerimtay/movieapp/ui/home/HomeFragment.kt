package com.akerimtay.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.local.SharedPrefsManager
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.databinding.FragmentHomeBinding
import com.akerimtay.movieapp.extensions.dpToPx
import com.akerimtay.movieapp.utils.ScrollStateHolder
import com.akerimtay.movieapp.utils.SpaceItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: FragmentHomeBinding
    private lateinit var moviesAdapter: CategoryWithMoviesAdapter
    private lateinit var scrollStateHolder: ScrollStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scrollStateHolder = ScrollStateHolder(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollStateHolder.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViews()
        checkIsInitializedDatabase()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViews() {
        binding.etSearch.setOnClickListener { openSearchPage() }
    }

    private fun checkIsInitializedDatabase() {
        with(SharedPrefsManager) {
            if (!isDataInitialized()) {
                viewModel.initCategories()
                setDataInitialized(true)
            }
        }
    }

    private fun setupRecyclerView() {
        moviesAdapter = CategoryWithMoviesAdapter(scrollStateHolder) { openMovieDetailsPage(it) }
        val spacing = requireContext().dpToPx(24)
        val itemDecoration = SpaceItemDecoration(spacing, SpaceItemDecoration.VERTICAL)
        binding.moviesRecycler.apply {
            adapter = moviesAdapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun observeViewModel() {
        viewModel.categoryWithMovies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    resource.data?.let {
                        moviesAdapter.setItems(it)
                        binding.txtEmpty.isVisible = it.isEmpty()
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun openSearchPage() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        val extras = FragmentNavigatorExtras(binding.etSearch to "search_view")
        findNavController().navigate(action, extras)
    }

    private fun openMovieDetailsPage(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie.id)
        findNavController().navigate(action)
    }
}