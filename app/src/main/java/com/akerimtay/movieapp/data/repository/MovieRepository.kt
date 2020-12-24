package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.model.Movies
import com.akerimtay.movieapp.utils.Resource

interface MovieRepository {
    suspend fun getTopRated(): Resource<Movies>
}