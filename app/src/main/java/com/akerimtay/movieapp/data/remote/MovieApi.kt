package com.akerimtay.movieapp.data.remote

import com.akerimtay.movieapp.data.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {
    @GET("/3/movie/top_rated?api_key=f222e97f710f0888bf9023a2fed819dd")
    suspend fun getTopRated(): Response<BaseResponse>
}