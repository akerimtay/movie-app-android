package com.akerimtay.movieapp.data.network

import com.akerimtay.movieapp.App
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.Resource
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

enum class NetworkErrors(val code: Int) {
    SocketTimeOut(-1),
    InvalidApiKey(401),
    NotFound(404);
}

open class ResponseHandler {

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        Timber.e("Error message = ${e.cause?.message}")
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(NetworkErrors.SocketTimeOut.code),
                null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        val app = App.getInstance()
        return when (code) {
            NetworkErrors.SocketTimeOut.code -> app.getString(R.string.error_timeout)
            NetworkErrors.InvalidApiKey.code -> app.getString(R.string.error_invalid_api_key)
            NetworkErrors.NotFound.code -> app.getString(R.string.error_not_found)
            else -> app.getString(R.string.something_went_wrong)
        }
    }

}