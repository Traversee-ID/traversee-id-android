package com.alvindev.traverseeid.feature_tourism.domain.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class TripEntity(
	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("trip_end")
	val tripEnd: String? = null,

	@field:SerializedName("trip_start")
	val tripStart: String? = null,

	@field:SerializedName("organizer")
	val organizer: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("images_url")
	val imagesUrl: List<String>,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("categories")
	val categories: List<String>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("regis_deadline")
	val regisDeadline: String? = null
) : Parcelable
