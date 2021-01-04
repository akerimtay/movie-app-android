package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.datasource.MovieLocalDataSource
import com.akerimtay.movieapp.data.datasource.MovieRemoteDataSource
import com.akerimtay.movieapp.data.model.MovieType
import com.akerimtay.movieapp.utils.performGetOperation

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override fun getPopular() = performGetOperation(
        databaseQuery = { localDataSource.getMovies(MovieType.POPULAR) },
        networkCall = { remoteDataSource.getPopular() },
        saveCallResult = { localDataSource.insertAll(it.movies, MovieType.POPULAR) }
    )

    override fun getTopRated() = performGetOperation(
        databaseQuery = { localDataSource.getMovies(MovieType.TOP_RATED) },
        networkCall = { remoteDataSource.getTopRated() },
        saveCallResult = { localDataSource.insertAll(it.movies, MovieType.TOP_RATED) }
    )

    override fun getNowPlaying() = performGetOperation(
        databaseQuery = { localDataSource.getMovies(MovieType.NOW_PLAYING) },
        networkCall = { remoteDataSource.getNowPlaying() },
        saveCallResult = { localDataSource.insertAll(it.movies, MovieType.NOW_PLAYING) }
    )

    override suspend fun getMovieDetails(movieId: Int) = remoteDataSource.getMovieDetails(movieId)

    override suspend fun getSimilar(movieId: Int) = remoteDataSource.getSimilar(movieId)

}