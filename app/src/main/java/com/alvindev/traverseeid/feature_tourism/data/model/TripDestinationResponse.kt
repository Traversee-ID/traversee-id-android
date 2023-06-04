package com.alvindev.traverseeid.feature_tourism.data.model

import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismEntity
import com.google.gson.annotations.SerializedName

data class TripDestinationResponse(
	@field:SerializedName("data")
	val data: List<TourismEntity>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)