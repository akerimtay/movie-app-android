package com.akerimtay.movieapp.ui.search

import androidx.lifecycle.ViewModel
import com.akerimtay.movieapp.data.repository.MovieRepository

class SearchViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun search(query: String?) {

    }
}