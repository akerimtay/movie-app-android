package com.akerimtay.movieapp.data.datasource.remote

import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.*
import com.akerimtay.movieapp.data.network.ResponseHandler
import com.akerimtay.movieapp.data.network.api.MovieApi

class MovieRemoteDataSource(
    private val movieApi: MovieApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getCategoryWithMovies(): Resource<List<CategoryWithMovies>> {
        return try {
            val popular = movieApi.getPopular()
            val topRated = movieApi.getTopRated()
            val nowPlaying = movieApi.getNowPlaying()

            val response = listOf(
                CategoryWithMovies(
                    Category(MovieType.POPULAR.id, MovieType.POPULAR.name),
                    popular.movies
                ),
                CategoryWithMovies(
                    Category(MovieType.TOP_RATED.id, MovieType.TOP_RATED.name),
                    topRated.movies
                ),
                CategoryWithMovies(
                    Category(MovieType.NOW_PLAYING.id, MovieType.NOW_PLAYING.name),
                    nowPlaying.movies
                )
            )
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> {
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

    suspend fun search(query: String, page: Int): Resource<List<Movie>> {
        return try {
            val response = movieApi.search(query, page)
            return responseHandler.handleSuccess(response.movies)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getVideos(movieId: Int): Resource<Videos> {
        return try {
            val response = movieApi.getVideos(movieId)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}