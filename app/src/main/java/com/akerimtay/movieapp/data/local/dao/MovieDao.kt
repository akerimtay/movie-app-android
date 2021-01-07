package com.akerimtay.movieapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.akerimtay.movieapp.data.local.entity.CategoryAndMovieEntity
import com.akerimtay.movieapp.data.local.entity.CategoryEntity
import com.akerimtay.movieapp.data.local.entity.CategoryWithMoviesEntity
import com.akerimtay.movieapp.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoriesAndMovies(categoryAndMovies: List<CategoryAndMovieEntity>)

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoryWithMovies(): LiveData<List<CategoryWithMoviesEntity>>

}