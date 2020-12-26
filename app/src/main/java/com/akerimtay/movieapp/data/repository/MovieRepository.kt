package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.model.Movies
import com.akerimtay.movieapp.data.Resource

interface MovieRepository {
    suspend fun getTopRated(): Resource<Movies>

    suspend fun getNowPlaying(): Resource<Movies>
}