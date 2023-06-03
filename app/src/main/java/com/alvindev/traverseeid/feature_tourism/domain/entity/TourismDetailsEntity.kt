package com.alvindev.traverseeid.feature_tourism.domain.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class TourismDetailsEntity(
	@field:SerializedName("description")
	val description: String? = null
) : Parcelable
