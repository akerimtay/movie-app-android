package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.network.datasource.MovieRemoteDataSource

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getDummy() = remoteDataSource.getDummy()
}