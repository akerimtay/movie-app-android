package com.akerimtay.movieapp.data.local.mapping

import androidx.room.TypeConverter
import com.akerimtay.movieapp.data.model.MovieType

class MovieTypeConverter {
    @TypeConverter
    fun movieTypeToString(movieType: MovieType?): String? {
        return movieType?.name
    }

    @TypeConverter
    fun stringToMovieType(movieType: String?): MovieType? {
        return movieType?.let { MovieType.find(it) }
    }
}