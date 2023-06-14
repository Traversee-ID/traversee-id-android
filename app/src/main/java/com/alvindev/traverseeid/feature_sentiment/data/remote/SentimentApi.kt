package com.alvindev.traverseeid.feature_sentiment.data.remote

import com.alvindev.traverseeid.feature_sentiment.data.model.SentimentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SentimentApi {
    @GET("analyze_sentiment")
    suspend fun getSentimentAnalysis(
        @Query("words") keyword: String
    ): Response<SentimentResponse>
}