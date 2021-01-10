package com.akerimtay.movieapp.data.datasource.remote

import com.akerimtay.movieapp.data.network.api.CreditApi

class CreditRemoteDataSource(private val creditApi: CreditApi) {

    suspend fun getCredits(movieId: Int) = creditApi.getCredits(movieId)

}