package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.model.Result

interface MovieRepository {
    suspend fun getTopRated(): Result
}