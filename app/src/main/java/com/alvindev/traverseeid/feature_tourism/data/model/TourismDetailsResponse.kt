package com.alvindev.traverseeid.feature_tourism.data.model

import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismDetailsEntity
import com.google.gson.annotations.SerializedName

data class TourismDetailsResponse(
	@field:SerializedName("data")
	val data: TourismDetailsEntity? = null,

	@field:SerializedName("message")
	val message: String? = null,
)