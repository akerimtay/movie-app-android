package com.akerimtay.movieapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.akerimtay.movieapp.data.model.CategoryAndMovie

@Entity(tableName = "category_and_movie", primaryKeys = ["category_id", "movie_id"])
data class CategoryAndMovieEntity(
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int
) {

    constructor(categoryAndMovie: CategoryAndMovie) : this(
        categoryAndMovie.categoryId,
        categoryAndMovie.movieId
    )

}