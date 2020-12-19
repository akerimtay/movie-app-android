package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.remote.datasource.MovieRemoteDataSource

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getDummy() = remoteDataSource.getDummy()
}