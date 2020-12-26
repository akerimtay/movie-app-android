package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.model.Movies
import com.akerimtay.movieapp.data.network.ResponseHandler
import com.akerimtay.movieapp.data.network.datasource.MovieRemoteDataSource
import com.akerimtay.movieapp.data.Resource

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val responseHandler: ResponseHandler
) : MovieRepository {

    override suspend fun getTopRated(): Resource<Movies> {
        return try {
            val response = remoteDataSource.getTopRated()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}