package com.akerimtay.movieapp.data.network.datasource

import com.akerimtay.movieapp.data.network.api.MovieApi

class MovieRemoteDataSource(private val movieApi: MovieApi) {

    suspend fun getMovieDetails(movieId: Int) = movieApi.getMovieDetails(movieId)

    suspend fun getTopRated() = movieApi.getTopRated()

    suspend fun getNowPlaying() = movieApi.getNowPlaying()

    suspend fun getPopular() = movieApi.getPopular()

}