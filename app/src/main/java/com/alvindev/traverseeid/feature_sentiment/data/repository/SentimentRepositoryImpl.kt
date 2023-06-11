package com.alvindev.traverseeid.feature_sentiment.data.repository

import com.alvindev.traverseeid.feature_sentiment.data.remote.SentimentApi
import com.alvindev.traverseeid.feature_sentiment.domain.repository.SentimentRepository
import javax.inject.Inject

class SentimentRepositoryImpl @Inject constructor(
    private val sentimentApi: SentimentApi
): SentimentRepository{

}