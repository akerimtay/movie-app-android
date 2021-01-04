package com.akerimtay.movieapp.data.datasource

import com.akerimtay.movieapp.data.local.dao.MovieDao
import com.akerimtay.movieapp.data.local.entity.MovieEntity

class MovieLocalDataSource(private val movieDao: MovieDao) {

    fun getMovies(type: String) = movieDao.getMovies(type)

    suspend fun insertAll(movies: List<MovieEntity>) = movieDao.insertAll(movies)

}