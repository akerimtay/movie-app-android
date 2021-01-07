package com.akerimtay.movieapp.data.model

import android.os.Parcelable
import com.akerimtay.movieapp.data.local.entity.CategoryWithMoviesEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryWithMovies(val category: Category, val movies: List<Movie>) : Parcelable {

    constructor(categoryWithMovies: CategoryWithMoviesEntity) : this(
        Category(categoryWithMovies.category),
        categoryWithMovies.movies.map { Movie(it) }
    )

}
