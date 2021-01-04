package com.akerimtay.movieapp.ui.home

import androidx.lifecycle.ViewModel
import com.akerimtay.movieapp.data.repository.MovieRepository

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val topRatedMovies = movieRepository.getTopRated()

    val nowPlayingMovies = movieRepository.getNowPlaying()

    val popularMovies = movieRepository.getPopular()

}