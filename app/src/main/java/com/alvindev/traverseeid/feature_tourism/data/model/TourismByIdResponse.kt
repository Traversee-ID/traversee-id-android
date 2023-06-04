package com.alvindev.traverseeid.feature_tourism.data.model

import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.google.gson.annotations.SerializedName

data class TourismByIdResponse(
	@field:SerializedName("data")
	val data: TourismItem? = null,

	@field:SerializedName("message")
	val message: String? = null
)
