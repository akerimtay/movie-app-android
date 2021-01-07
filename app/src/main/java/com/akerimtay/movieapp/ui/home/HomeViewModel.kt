package com.akerimtay.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akerimtay.movieapp.data.model.Category
import com.akerimtay.movieapp.data.model.MovieType
import com.akerimtay.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val categoryWithMovies = movieRepository.getCategoryWithMovies()

    fun initCategories() {
        viewModelScope.launch {
            val categories = listOf(
                Category(MovieType.POPULAR.id, MovieType.POPULAR.name),
                Category(MovieType.TOP_RATED.id, MovieType.TOP_RATED.name),
                Category(MovieType.NOW_PLAYING.id, MovieType.NOW_PLAYING.name)
            )
            movieRepository.insertCategories(categories)
        }
    }
}