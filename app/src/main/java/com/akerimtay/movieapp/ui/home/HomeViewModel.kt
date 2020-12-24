package com.akerimtay.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.utils.Resource

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies = liveData {
        emit(Resource.loading(null))
        emit(movieRepository.getTopRated())
    }

}