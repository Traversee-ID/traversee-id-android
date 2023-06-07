package com.alvindev.traverseeid.feature_tourism.domain.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class TourismEntity(
	@field:SerializedName("location_name")
	val locationName: String? = null,

	@field:SerializedName("category_name")
	val categoryName: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("trip_id")
	val tripId: Int? = null,
) : Parcelable
