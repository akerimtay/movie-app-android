package com.akerimtay.movieapp.ui.search

import androidx.lifecycle.*
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.repository.MovieRepository

class SearchViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    val movies = query.switchMap {
        liveData {
            emit(Resource.loading())
            emit(movieRepository.search(it))
        }
    }

    fun search(query: String?) {
        if (query == null || query.trim().isEmpty()) return
        _query.value = query
    }
}