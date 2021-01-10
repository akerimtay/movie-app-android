package com.akerimtay.movieapp.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.akerimtay.movieapp.data.local.dao.MovieDao
import com.akerimtay.movieapp.data.local.entity.CategoryAndMovieEntity
import com.akerimtay.movieapp.data.local.entity.CategoryEntity
import com.akerimtay.movieapp.data.local.entity.MovieEntity
import com.akerimtay.movieapp.data.model.Category
import com.akerimtay.movieapp.data.model.CategoryAndMovie
import com.akerimtay.movieapp.data.model.CategoryWithMovies
import com.akerimtay.movieapp.data.model.Movie

class MovieLocalDataSource(private val movieDao: MovieDao) {

    fun getCategoryWithMovies(): LiveData<List<CategoryWithMovies>> {
        val data = movieDao.getCategoryWithMovies()
        return Transformations.map(data) { categoryWithMovies ->
            categoryWithMovies.map { CategoryWithMovies(it) }
        }
    }

    suspend fun insertCategories(categories: List<Category>) {
        val data = categories.map { CategoryEntity(it) }
        movieDao.insertCategories(data)
    }

    suspend fun insertCategoriesWithMovies(data: List<CategoryWithMovies>) {
        val movies = mutableListOf<Movie>()
        val categoryAndMovies = mutableListOf<CategoryAndMovie>()

        // TODO: Optimize it
        for (item in data) {
            movies.addAll(item.movies)
            item.movies.forEach { movie ->
                categoryAndMovies.add(CategoryAndMovie(item.category.id, movie.id))
            }
        }

        val moviesEntity = movies.map { MovieEntity(it) }
        movieDao.insertMovies(moviesEntity)

        val categoriesAndMoviesEntity = categoryAndMovies.map { CategoryAndMovieEntity(it) }
        movieDao.insertCategoriesAndMovies(categoriesAndMoviesEntity)
    }

}