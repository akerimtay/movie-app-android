package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.datasource.MovieLocalDataSource
import com.akerimtay.movieapp.data.datasource.MovieRemoteDataSource
import com.akerimtay.movieapp.data.model.Category
import com.akerimtay.movieapp.utils.performGetOperation

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun insertCategories(categories: List<Category>) =
        localDataSource.insertCategories(categories)

    override fun getCategoryWithMovies() = performGetOperation(
        databaseQuery = { localDataSource.getCategoryWithMovies() },
        networkCall = { remoteDataSource.getCategoryWithMovies() },
        saveCallResult = { localDataSource.insertCategoriesWithMovies(it) }
    )

    override suspend fun getMovieDetails(movieId: Int) = remoteDataSource.getMovieDetails(movieId)

    override suspend fun getSimilar(movieId: Int) = remoteDataSource.getSimilar(movieId)

}