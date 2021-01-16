package com.akerimtay.movieapp.data.model

import android.os.Parcelable
import com.akerimtay.movieapp.data.local.entity.MovieEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.math.RoundingMode
import java.util.*

@Parcelize
data class Movies(
    @SerializedName("results") val movies: List<Movie>
) : Parcelable

@Parcelize
data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val isAdult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: Date?,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) : Parcelable {

    constructor(movie: MovieEntity) : this(
        movie.id, movie.isAdult, movie.backdropPath, movie.originalLanguage,
        movie.originalTitle, movie.overview, movie.popularity, movie.posterPath, movie.releaseDate,
        movie.title, movie.video, movie.voteAverage, movie.voteCount
    )

    fun getVote(): String {
        return voteAverage.toBigDecimal()
            .setScale(1, RoundingMode.UP)
            .toDouble()
            .toString()
    }

}