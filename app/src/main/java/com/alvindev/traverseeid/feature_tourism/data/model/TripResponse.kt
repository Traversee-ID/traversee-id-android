package com.alvindev.traverseeid.feature_tourism.data.model

import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity
import com.google.gson.annotations.SerializedName

data class TripResponse(
	@field:SerializedName("data")
	val data: List<TripEntity>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)