package com.alvindev.traverseeid.feature_forum.data.model

import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem
import com.google.gson.annotations.SerializedName

data class ForumResponse(
	@field:SerializedName("data")
	val data: List<ForumPostItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)