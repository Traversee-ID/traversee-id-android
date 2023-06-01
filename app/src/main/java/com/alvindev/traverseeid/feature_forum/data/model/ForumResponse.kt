package com.alvindev.traverseeid.feature_forum.data.model

import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostEntity
import com.google.gson.annotations.SerializedName

data class ForumResponse(
	@field:SerializedName("data")
	val data: List<ForumPostEntity>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)