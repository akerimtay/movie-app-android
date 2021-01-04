package com.akerimtay.movieapp.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.akerimtay.movieapp.data.local.dao.MovieDao
import com.akerimtay.movieapp.data.local.entity.MovieEntity
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.data.model.MovieType

class MovieLocalDataSource(private val movieDao: MovieDao) {

    fun getMovies(type: MovieType): LiveData<List<Movie>> {
        val source = movieDao.getMovies(type.type)
        return Transformations.map(source) { movies -> movies.map { Movie(it) } }
    }

    suspend fun insertAll(movies: List<Movie>, movieType: MovieType) {
        val result = movies.map { MovieEntity(it, movieType) }
        movieDao.insertAll(result)
    }

}