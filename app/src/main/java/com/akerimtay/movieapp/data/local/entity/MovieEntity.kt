package com.akerimtay.movieapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akerimtay.movieapp.data.model.Movie
import com.akerimtay.movieapp.data.model.MovieType
import java.util.*

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "adult") val isAdult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: Date?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "type") val type: MovieType
) {
    constructor(movie: Movie, type: MovieType) : this(
        movie.id, movie.isAdult, movie.backdropPath, movie.originalLanguage,
        movie.originalTitle, movie.overview, movie.popularity, movie.posterPath, movie.releaseDate,
        movie.title, movie.video, movie.voteAverage, movie.voteCount, type
    )
}