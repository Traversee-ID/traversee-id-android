package com.alvindev.traverseeid.di

import com.alvindev.traverseeid.feature_sentiment.data.remote.SentimentApi
import com.alvindev.traverseeid.feature_sentiment.data.repository.SentimentRepositoryImpl
import com.alvindev.traverseeid.feature_sentiment.domain.repository.SentimentRepository
import com.alvindev.traverseeid.feature_sentiment.domain.use_case.GetSentimentAnalysis
import com.alvindev.traverseeid.feature_sentiment.domain.use_case.UseCasesSentiment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SentimentModule {
    @Provides
    @Singleton
    fun provideSentimentRepository(sentimentApi: SentimentApi): SentimentRepository {
        return SentimentRepositoryImpl(sentimentApi)
    }

    @Provides
    @Singleton
    fun provideTourismApi(): SentimentApi {
        return ApiConfig.getApiService(SentimentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTourismUseCases(repository: SentimentRepository): UseCasesSentiment {
        return UseCasesSentiment(
            getSentimentAnalysis = GetSentimentAnalysis(repository),
        )
    }
}