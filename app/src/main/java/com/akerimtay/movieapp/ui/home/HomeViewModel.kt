package com.akerimtay.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akerimtay.movieapp.App
import com.akerimtay.movieapp.NetworkCodes
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.data.network.ErrorResponse
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    fun loadTopRatedMovies() {
        viewModelScope.launch {
            _movies.value = Resource.loading(data = null)
            try {
                val data = movieRepository.getTopRated().movies
                _movies.value = Resource.success(data = data)
            } catch (exception: Throwable) {
                val response = ErrorResponse.fromThrowable(exception)
                val message = handleException(response)
                _movies.value = Resource.error(data = null, message = message)
            }
        }
    }

    private fun handleException(response: ErrorResponse?): String {
        val resource = when (response?.code) {
            NetworkCodes.CODE_INVALID_API_KEY -> R.string.error_invalid_api_key
            NetworkCodes.CODE_NOT_FOUND -> R.string.error_not_found
            else -> R.string.something_went_wrong
        }
        return App.getInstance().getString(resource)
    }
}