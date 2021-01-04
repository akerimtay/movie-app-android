package com.akerimtay.movieapp.data.model

enum class MovieType(val type: String) {
    POPULAR("POPULAR"),
    TOP_RATED("TOP_RATED"),
    NOW_PLAYING("NOW_PLAYING"),
    UNKNOWN("UNKNOWN");

    companion object {
        private val map = values().associateBy(MovieType::type)

        fun find(value: String) = map[value] ?: UNKNOWN
    }
}