package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.MovieFull
import com.akerimtay.movieapp.data.model.Movies
import com.akerimtay.movieapp.data.network.ResponseHandler
import com.akerimtay.movieapp.data.network.datasource.MovieRemoteDataSource

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val responseHandler: ResponseHandler
) : MovieRepository {

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieFull> {
        return try {
            val response = remoteDataSource.getMovieDetails(movieId)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getTopRated(): Resource<Movies> {
        return try {
            val response = remoteDataSource.getTopRated()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getNowPlaying(): Resource<Movies> {
        return try {
            val response = remoteDataSource.getNowPlaying()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getPopular(): Resource<Movies> {
        return try {
            val response = remoteDataSource.getPopular()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getSimilar(movieId: Int): Resource<Movies> {
        return try {
            val response = remoteDataSource.getSimilar(movieId)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}