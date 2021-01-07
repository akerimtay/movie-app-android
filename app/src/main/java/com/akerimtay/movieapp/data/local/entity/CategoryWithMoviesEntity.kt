package com.akerimtay.movieapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CategoryWithMoviesEntity(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = MovieEntity::class,
        associateBy = Junction(
            CategoryAndMovieEntity::class,
            parentColumn = "category_id",
            entityColumn = "movie_id"
        )
    )
    val movies: List<MovieEntity>
)