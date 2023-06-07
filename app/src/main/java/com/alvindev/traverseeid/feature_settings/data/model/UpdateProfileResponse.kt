package com.alvindev.traverseeid.feature_settings.data.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("message")
	val message: String? = null,
)
