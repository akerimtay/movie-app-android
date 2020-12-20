package com.akerimtay.movieapp.data.network.datasource

import com.akerimtay.movieapp.data.network.api.MovieApi

class MovieRemoteDataSource(private val movieApi: MovieApi) : BaseDataSource() {
    suspend fun getDummy() = getResult { movieApi.getTopRated() }
}