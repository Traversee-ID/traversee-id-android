package com.alvindev.traverseeid.feature_sentiment.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_sentiment.data.remote.SentimentApi
import com.alvindev.traverseeid.feature_sentiment.domain.entity.SentimentEntity
import com.alvindev.traverseeid.feature_sentiment.domain.mapper.SentimentMapper
import com.alvindev.traverseeid.feature_sentiment.domain.repository.SentimentRepository
import org.json.JSONException
import javax.inject.Inject

class SentimentRepositoryImpl @Inject constructor(
    private val sentimentApi: SentimentApi
): SentimentRepository{
    override suspend fun getSentimentAnalysis(keyword: String): LiveData<ResultState<List<SentimentEntity>>> = liveData {
        try {
            emit(ResultState.Loading)
            val response = sentimentApi.getSentimentAnalysis(keyword)
            if (response.isSuccessful) {
                val responseData = response.body()?.data
                responseData?.let {
                    val sentimentList = it.map { item -> SentimentMapper.mapSentimentResponseToSentimentEntity(item.key, item.value) }
                    emit(ResultState.Success(sentimentList))
                } ?: emit(ResultState.Error("Unexpected Error!"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val errorJson = org.json.JSONObject(errorBody)
                    errorJson.getString("message")
                } catch (e: JSONException) {
                    "Error: ${response.code()} ${response.message()}"
                }
                emit(ResultState.Error(errorMessage))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

}