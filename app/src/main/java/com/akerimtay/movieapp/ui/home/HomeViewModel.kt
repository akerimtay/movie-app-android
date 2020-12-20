package com.akerimtay.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akerimtay.movieapp.data.BaseResponse
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _data = MutableLiveData<Resource<BaseResponse>>()
    val data: LiveData<Resource<BaseResponse>>
        get() = _data

    fun loadData() {
        viewModelScope.launch {
            _data.value = movieRepository.getDummy()
        }
    }
}