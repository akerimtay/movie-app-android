package com.akerimtay.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.repository.MovieRepository

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val topRatedMovies = liveData {
        emit(Resource.loading(null))
        emit(movieRepository.getTopRated())
    }

    val nowPlayingMovies = liveData {
        emit(Resource.loading(null))
        emit(movieRepository.getNowPlaying())
    }

}