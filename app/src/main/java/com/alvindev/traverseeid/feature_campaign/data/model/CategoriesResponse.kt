package com.alvindev.traverseeid.feature_campaign.data.model

import com.alvindev.traverseeid.feature_campaign.domain.entity.CategoryEntity
import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
	@field:SerializedName("data")
	val data: List<CategoryEntity>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

