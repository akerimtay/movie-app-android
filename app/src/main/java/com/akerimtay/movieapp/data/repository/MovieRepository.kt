package com.akerimtay.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.data.model.MovieFull
import com.akerimtay.movieapp.data.model.Movies

interface MovieRepository {

    fun getPopular(): LiveData<Resource<List<Movie>>>

    fun getTopRated(): LiveData<Resource<List<Movie>>>

    fun getNowPlaying(): LiveData<Resource<List<Movie>>>

    suspend fun getMovieDetails(movieId: Int): Resource<MovieFull>

    suspend fun getSimilar(movieId: Int): Resource<Movies>
}