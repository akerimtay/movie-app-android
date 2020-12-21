package com.akerimtay.movieapp.data.network.datasource

import com.akerimtay.movieapp.data.network.api.MovieApi

class MovieRemoteDataSource(private val movieApi: MovieApi) {
    suspend fun getTopRated() = movieApi.getTopRated()
}