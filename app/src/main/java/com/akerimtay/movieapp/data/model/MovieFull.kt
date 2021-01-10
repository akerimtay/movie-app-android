package com.akerimtay.movieapp.data.model

import android.content.Context
import android.os.Parcelable
import com.akerimtay.movieapp.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.math.RoundingMode
import java.util.*

@Parcelize
data class MovieFull(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: Date?,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagLine: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("production_companies") val companies: List<Company>
) : Parcelable {

    fun getVote(): String {
        return voteAverage.toBigDecimal().setScale(1, RoundingMode.UP).toDouble().toString()
    }

    fun getRunTime(context: Context): String {
        var time = runtime
        var hour = 0
        while (time > 60) {
            time -= 60
            hour++
        }
        return context.resources.getString(R.string.movie_runtime_format, hour, time)
    }

    fun getCompaniesString(): String {
        val sb = StringBuilder()
        var separator = ""
        companies.forEach {
            sb.append(separator)
            sb.append(it.name)
            separator = ", "
        }
        return sb.toString()
    }

    fun getGenresString(): String {
        val sb = StringBuilder()
        var separator = ""
        genres.forEach {
            sb.append(separator)
            sb.append(it.name.capitalize(Locale.ROOT))
            separator = ", "
        }
        return sb.toString()
    }

}

@Parcelize
data class Company(
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("name") val name: String
) : Parcelable