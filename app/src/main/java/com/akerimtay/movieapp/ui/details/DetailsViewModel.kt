package com.akerimtay.movieapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.data.repository.MovieRepository

class DetailsViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    val movie = MutableLiveData<Movie>()
}