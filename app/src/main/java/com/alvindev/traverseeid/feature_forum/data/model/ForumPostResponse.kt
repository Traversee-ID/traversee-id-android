package com.alvindev.traverseeid.feature_forum.data.model

import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem
import com.google.gson.annotations.SerializedName

data class ForumPostResponse(
	@field:SerializedName("data")
	val data: ForumPostItem? = null,

	@field:SerializedName("message")
	val message: String? = null,
)


