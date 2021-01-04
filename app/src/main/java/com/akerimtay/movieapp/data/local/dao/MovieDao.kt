package com.akerimtay.movieapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akerimtay.movieapp.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getMovies(type: String): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

}