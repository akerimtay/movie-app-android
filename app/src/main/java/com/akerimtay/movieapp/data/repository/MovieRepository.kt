package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.MovieFull
import com.akerimtay.movieapp.data.model.Movies

interface MovieRepository {

    suspend fun getMovieDetails(movieId: Int): Resource<MovieFull>

    suspend fun getTopRated(): Resource<Movies>

    suspend fun getNowPlaying(): Resource<Movies>

    suspend fun getPopular(): Resource<Movies>

    suspend fun getSimilar(movieId: Int): Resource<Movies>

}