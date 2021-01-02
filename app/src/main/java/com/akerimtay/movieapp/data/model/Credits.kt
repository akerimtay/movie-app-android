package com.akerimtay.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<Cast>,
    @SerializedName("crew") val crew: List<Crew>
) {
    fun getCastAndCrew() = mutableListOf<Credit>().apply {
        addAll(cast.map {
            Credit(
                name = it.name,
                caption = it.character,
                profilePath = it.profilePath
            )
        })
        addAll(crew.map {
            Credit(
                name = it.name,
                caption = it.job,
                profilePath = it.profilePath
            )
        })
    }
}

data class Cast(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("order") val order: Int
)

data class Crew(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("department") val department: String,
    @SerializedName("job") val job: String
)

data class Credit(
    val caption: String,
    val name: String,
    val profilePath: String?
)