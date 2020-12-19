package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.BaseResponse
import com.akerimtay.movieapp.utils.Resource

interface MovieRepository {
    suspend fun getDummy(): Resource<BaseResponse>
}