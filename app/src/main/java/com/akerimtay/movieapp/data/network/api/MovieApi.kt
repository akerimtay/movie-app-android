package com.akerimtay.movieapp.data.network.api

import com.akerimtay.movieapp.data.model.Movies
import retrofit2.http.GET

interface MovieApi {
    @GET("movie/top_rated")
    suspend fun getTopRated(): Movies

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): Movies

    @GET("movie/popular")
    suspend fun getPopular(): Movies
}