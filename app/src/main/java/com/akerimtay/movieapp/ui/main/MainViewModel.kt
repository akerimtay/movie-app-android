package com.akerimtay.movieapp.ui.main

import androidx.lifecycle.ViewModel
import com.akerimtay.movieapp.data.repository.MovieRepository

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getDummyData(): String {
        return movieRepository.getDummy()
    }
}