package com.akerimtay.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.Category
import com.akerimtay.movieapp.data.model.CategoryWithMovies
import com.akerimtay.movieapp.data.model.MovieFull
import com.akerimtay.movieapp.data.model.Movies

interface MovieRepository {

    fun getCategoryWithMovies(): LiveData<Resource<List<CategoryWithMovies>>>

    suspend fun insertCategories(categories: List<Category>)

    suspend fun getMovieDetails(movieId: Int): Resource<MovieFull>

    suspend fun getSimilar(movieId: Int): Resource<Movies>
}