package com.akerimtay.movieapp.data.model

import android.os.Parcelable
import com.akerimtay.movieapp.data.local.entity.CategoryEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Parcelable {

    constructor(category: CategoryEntity) : this(category.id, category.name)

}
