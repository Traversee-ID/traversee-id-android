package com.alvindev.traverseeid.feature_forum.data.model

import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCommentEntity
import com.google.gson.annotations.SerializedName

data class ForumCreateCommentResponse(
	@field:SerializedName("data")
	val data: ForumCommentEntity? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

