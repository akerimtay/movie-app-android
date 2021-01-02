package com.akerimtay.movieapp.data.network.api

import com.akerimtay.movieapp.data.model.Credits
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditApi {

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(@Path("movie_id") movieId: Int): Credits

}