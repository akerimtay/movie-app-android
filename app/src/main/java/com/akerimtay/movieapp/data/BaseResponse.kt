package com.akerimtay.movieapp.data

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)