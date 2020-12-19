package com.akerimtay.movieapp.data.repository

import com.akerimtay.movieapp.data.remote.MovieApi

class MovieRepositoryImpl(private val api: MovieApi) : MovieRepository {
    override fun getDummy(): String {
        return "Hello World"
    }
}