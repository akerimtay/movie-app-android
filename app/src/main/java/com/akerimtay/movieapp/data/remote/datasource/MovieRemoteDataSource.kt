package com.akerimtay.movieapp.data.remote.datasource

import com.akerimtay.movieapp.data.remote.MovieApi

class MovieRemoteDataSource(private val movieApi: MovieApi) : BaseDataSource() {
    suspend fun getDummy() = getResult { movieApi.getTopRated() }
}