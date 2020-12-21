package com.akerimtay.movieapp.data.network

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import java.io.IOException

class ErrorResponse {
    @SerializedName("status_code")
    var code: String? = null
        internal set
    @SerializedName("status_message")
    var message: String? = null
        internal set

    companion object {

        fun fromThrowable(e: Throwable): ErrorResponse? {
            var response: ErrorResponse? = null
            if (e is HttpException) {
                try {
                    val errorBody = e.response()?.errorBody()?.string()
                    response = Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            }
            return response
        }
    }
}