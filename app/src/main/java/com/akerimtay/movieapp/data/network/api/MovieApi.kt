package com.akerimtay.movieapp.data.network.api

import com.akerimtay.movieapp.data.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {
    @GET("/3/movie/top_rated")
    suspend fun getTopRated(): Response<BaseResponse>
}