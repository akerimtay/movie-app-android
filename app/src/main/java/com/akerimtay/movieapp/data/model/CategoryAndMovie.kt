package com.akerimtay.movieapp.data.model

import android.os.Parcelable
import com.akerimtay.movieapp.data.local.entity.CategoryAndMovieEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryAndMovie(val categoryId: Int, val movieId: Int) : Parcelable {

    constructor(categoryAndMovie: CategoryAndMovieEntity) : this(
        categoryAndMovie.categoryId,
        categoryAndMovie.movieId
    )

}
