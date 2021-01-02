package com.akerimtay.movieapp.ui.details

import androidx.lifecycle.*
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.repository.CreditRepository
import com.akerimtay.movieapp.data.repository.MovieRepository

class DetailsViewModel(
    private val movieRepository: MovieRepository,
    private val creditRepository: CreditRepository
) : ViewModel() {

    private val _movieId = MutableLiveData(0)
    val movieId: LiveData<Int>
        get() = _movieId

    val movie = movieId.switchMap { id ->
        liveData {
            emit(Resource.loading(null))
            emit(movieRepository.getMovieDetails(id))
        }
    }

    val credits = movieId.switchMap { id ->
        liveData {
            emit(creditRepository.getCredits(id))
        }
    }

    fun setMovieId(id: Int) {
        _movieId.value = id
    }

}