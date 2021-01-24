package com.akerimtay.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results") val videos: List<Video>
)

data class Video(
    @SerializedName("id") val id: String,
    @SerializedName("iso_639_1") val iso_639_1: String,
    @SerializedName("iso_3166_1") val iso_3166_1: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("size") val size: Int,
    @SerializedName("type") val type: String
) {
    fun buildYouTubeThumbnailURL(): String {
        return "https://img.youtube.com/vi/$key/0.jpg"
    }
}