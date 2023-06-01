package com.alvindev.traverseeid.feature_forum.data.model

import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCommentEntity
import com.google.gson.annotations.SerializedName

data class ForumCommentResponse(
	@field:SerializedName("data")
	val data: List<ForumCommentEntity>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

