package com.akerimtay.movieapp.data.model

import android.content.Context
import android.os.Parcelable
import com.akerimtay.movieapp.R
import com.akerimtay.movieapp.data.local.entity.CategoryWithMoviesEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryWithMovies(val category: Category, val movies: List<Movie>) : Parcelable {

    constructor(categoryWithMovies: CategoryWithMoviesEntity) : this(
        Category(categoryWithMovies.category),
        categoryWithMovies.movies.map { Movie(it) }
    )

    fun getCategoryName(context: Context): String {
        return when (category.id) {
            MovieType.POPULAR.id -> context.getString(R.string.popular)
            MovieType.NOW_PLAYING.id -> context.getString(R.string.now_playing)
            MovieType.TOP_RATED.id -> context.getString(R.string.top_rated)
            else -> context.getString(R.string.unknown)
        }
    }

}
