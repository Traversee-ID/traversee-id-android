package com.alvindev.traverseeid.feature_tourism.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TourismItem(
    @field:SerializedName("is_favorite")
    val isFavorite: Boolean,

    @field:SerializedName("tourism")
    val tourism: TourismEntity
):Parcelable
