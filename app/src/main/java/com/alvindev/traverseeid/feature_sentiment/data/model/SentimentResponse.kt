package com.alvindev.traverseeid.feature_sentiment.data.model

import com.alvindev.traverseeid.feature_sentiment.domain.entity.SentimentEntity
import com.google.gson.annotations.SerializedName

data class SentimentResponse(
	@field:SerializedName("data")
	val data: Map<String, SentimentEntity>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)