package com.alvindev.traverseeid.feature_sentiment.domain.use_case

import com.alvindev.traverseeid.feature_sentiment.domain.repository.SentimentRepository

class GetSentimentAnalysis(
    private val sentimentRepository: SentimentRepository
) {
}