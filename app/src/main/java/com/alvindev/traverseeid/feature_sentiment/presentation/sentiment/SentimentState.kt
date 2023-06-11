package com.alvindev.traverseeid.feature_sentiment.presentation.sentiment

data class SentimentState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val queryResult: String = ""
)
