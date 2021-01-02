package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.Credits
import com.akerimtay.movieapp.data.network.ResponseHandler
import com.akerimtay.movieapp.data.network.datasource.CreditRemoteDataSource

class CreditRepositoryImpl(
    private val remoteDataSource: CreditRemoteDataSource,
    private val responseHandler: ResponseHandler
) : CreditRepository {

    override suspend fun getCredits(movieId: Int): Resource<Credits> {
        return try {
            val response = remoteDataSource.getCredits(movieId)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}