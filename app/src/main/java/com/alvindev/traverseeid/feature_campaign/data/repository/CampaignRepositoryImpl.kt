package com.alvindev.traverseeid.feature_campaign.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_campaign.data.model.*
import com.alvindev.traverseeid.feature_campaign.data.paging_source.CampaignsPagingSource
import com.alvindev.traverseeid.feature_campaign.data.remote.CampaignApi
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import org.json.JSONException
import org.json.JSONObject

class CampaignRepositoryImpl(
    private val campaignApi: CampaignApi
) : CampaignRepository {
    override suspend fun getCategories(): LiveData<ResultState<List<CategoryEntity>>> = liveData {
        try {
            val response = campaignApi.getCategories()
            response.data?.let {
                emit(ResultState.Success(it))
            } ?: emit(ResultState.Error(response.message.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override fun getAllCampaigns(
        params: CampaignParams
    ): Flow<PagingData<CampaignItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                CampaignsPagingSource(
                    campaignApi,
                    campaignParams = params,
                )
            }
        ).flow
    }

    override suspend fun getCampaignById(campaignId: Int): LiveData<ResultState<CampaignItem>> =
        liveData {
            try {
                val response = campaignApi.getCampaignById(campaignId)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override fun getCampaignsByCategory(
        categoryId: Int,
        params: CampaignParams
    ): Flow<PagingData<CampaignItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                CampaignsPagingSource(
                    campaignApi,
                    categoryId,
                    campaignParams = params,
                )
            }
        ).flow
    }

    override suspend fun getFirstPageRegisteredCampaigns(): LiveData<ResultState<List<CampaignItem>>> =
        liveData {
            try {
                emit(ResultState.Loading)

                val response = campaignApi.getAllCampaigns(
                    page = 1,
                    isRegistered = true,
                )
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getCampaignDetails(campaignId: Int): LiveData<ResultState<CampaignDetailsData>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = campaignApi.getCampaignDetails(campaignId)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getCampaignParticipants(campaignId: Int): LiveData<ResultState<List<CampaignParticipantsItem>>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = campaignApi.getCampaignParticipants(campaignId)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun registerCampaign(campaignId: Int): LiveData<ResultState<CampaignRegisterData>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = campaignApi.registerCampaign(campaignId)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
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

    override suspend fun submitCampaign(
        campaignId: Int,
        submissionUrl: String
    ): LiveData<ResultState<CampaignSubmissionData>> = liveData {
        try {
            emit(ResultState.Loading)
            val body = CampaignSubmissionBody(submissionUrl)
            val response = campaignApi.submitCampaign(campaignId, body)
            if (response.isSuccessful) {
                val responseData = response.body()?.data
                responseData?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error("Unexpected Error!"))
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val errorJson = JSONObject(errorBody)
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

    override suspend fun getCampaignLocations(): LiveData<ResultState<List<LocationEntity>>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = campaignApi.getCampaignLocations()
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }
}