package com.alvindev.traverseeid.feature_sentiment.domain.repository

import androidx.lifecycle.LiveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_sentiment.domain.entity.SentimentEntity

interface SentimentRepository {
    suspend fun getSentimentAnalysis(keyword: String): LiveData<ResultState<List<SentimentEntity>>>
}