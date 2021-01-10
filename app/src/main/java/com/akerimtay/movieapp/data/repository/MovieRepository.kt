package com.akerimtay.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.akerimtay.movieapp.data.Resource
import com.akerimtay.movieapp.data.model.*

interface MovieRepository {

    fun getCategoryWithMovies(): LiveData<Resource<List<CategoryWithMovies>>>

    suspend fun insertCategories(categories: List<Category>)

    suspend fun getMovieDetails(movieId: Int): Resource<MovieFull>

    suspend fun getSimilar(movieId: Int): Resource<Movies>

    suspend fun search(query: String, page: Int): Resource<List<Movie>>

}