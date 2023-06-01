package com.alvindev.traverseeid.feature_forum.data.model

import com.google.gson.annotations.SerializedName

data class ForumDeleteCommentResponse(
	@field:SerializedName("message")
	val message: String? = null,
)
