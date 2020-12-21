package com.akerimtay.movieapp.data.network.api

import com.akerimtay.movieapp.data.model.Result
import retrofit2.http.GET

interface MovieApi {
    @GET("/3/movie/top_rated")
    suspend fun getTopRated(): Result
}