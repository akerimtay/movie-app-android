package com.akerimtay.movieapp.data.datasource

import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.MovieFull
import com.akerimtay.movieapp.data.model.Movies
import com.akerimtay.movieapp.data.network.ResponseHandler
import com.akerimtay.movieapp.data.network.api.MovieApi

class MovieRemoteDataSource(
    private val movieApi: MovieApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getPopular(): Resource<Movies> {
        return try {
            val response = movieApi.getPopular()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getTopRated(): Resource<Movies> {
        return try {
            val response = movieApi.getTopRated()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getNowPlaying(): Resource<Movies> {
        return try {
            val response = movieApi.getNowPlaying()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getMovieDetails(movieId: Int): Resource<MovieFull> {
        return try {
            val response = movieApi.getMovieDetails(movieId)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getSimilar(movieId: Int): Resource<Movies> {
        return try {
            val response = movieApi.getSimilar(movieId)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}