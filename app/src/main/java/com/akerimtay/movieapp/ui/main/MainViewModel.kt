package com.akerimtay.movieapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akerimtay.movieapp.data.BaseResponse
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    val data = MutableLiveData<Resource<BaseResponse>>()

    fun getDummyData() {
        viewModelScope.launch {
            val result = movieRepository.getDummy()
            data.value = result
        }
    }
}