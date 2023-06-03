package com.alvindev.traverseeid.feature_tourism.data.model

import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.google.gson.annotations.SerializedName

data class TourismResponse(
	@field:SerializedName("data")
	val data: List<TourismItem>? = null,

	@field:SerializedName("message")
	val message: String? = null
)
