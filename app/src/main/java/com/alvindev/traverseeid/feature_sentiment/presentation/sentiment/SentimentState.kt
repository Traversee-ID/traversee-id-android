package com.alvindev.traverseeid.feature_sentiment.presentation.sentiment

import com.alvindev.traverseeid.feature_sentiment.domain.entity.SentimentEntity

data class SentimentState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val queryResult: String = "",
    val sentimentData: List<SentimentEntity>? = null,
)
