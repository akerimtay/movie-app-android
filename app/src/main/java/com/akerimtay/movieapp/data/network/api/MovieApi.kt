package com.akerimtay.movieapp.data.network.api

import com.akerimtay.movieapp.data.model.MovieDetail
import com.akerimtay.movieapp.data.model.Movies
import com.akerimtay.movieapp.data.model.Videos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetail

    @GET("movie/top_rated")
    suspend fun getTopRated(): Movies

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): Movies

    @GET("movie/popular")
    suspend fun getPopular(): Movies

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(@Path("movie_id") movieId: Int): Movies

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Movies

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(@Path("movie_id") movieId: Int): Videos

}