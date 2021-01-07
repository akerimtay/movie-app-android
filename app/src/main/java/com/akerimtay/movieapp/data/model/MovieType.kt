package com.akerimtay.movieapp.data.model

enum class MovieType(val id: Int, val type: String) {
    POPULAR(1, "POPULAR"),
    TOP_RATED(2, "TOP_RATED"),
    NOW_PLAYING(3, "NOW_PLAYING"),
    UNKNOWN(0, "UNKNOWN");

    companion object {
        private val map = values().associateBy(MovieType::type)

        fun find(value: String) = map[value] ?: UNKNOWN
    }
}