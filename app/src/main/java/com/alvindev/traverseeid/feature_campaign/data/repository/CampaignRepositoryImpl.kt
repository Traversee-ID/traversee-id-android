package com.alvindev.traverseeid.feature_campaign.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_campaign.data.model.*
import com.alvindev.traverseeid.feature_campaign.data.paging_source.CampaignsPagingSource
import com.alvindev.traverseeid.feature_campaign.data.remote.CampaignApi
import com.alvindev.traverseeid.feature_campaign.domain.entity.CategoryEntity
import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow

class CampaignRepositoryImpl(
    private val campaignApi: CampaignApi,
    private val authenticator: BaseAuthRepository,
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

    override fun getAllCampaigns(status: String?): Flow<PagingData<CampaignItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                CampaignsPagingSource(campaignApi, status = status)
            }
        ).flow
    }

    override fun getCampaignsByCategory(
        categoryId: Int,
        status: String?
    ): Flow<PagingData<CampaignItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                CampaignsPagingSource(campaignApi, categoryId, status)
            }
        ).flow
    }

    override fun getRegisteredCampaigns(): Flow<PagingData<CampaignItem>> {
        val userId = authenticator.getCurrentUser()?.uid
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                CampaignsPagingSource(campaignApi, userId = userId)
            }
        ).flow
    }

    override suspend fun getFirstPageRegisteredCampaigns(): LiveData<ResultState<List<CampaignItem>>> =
        liveData {
            try {
                val userId = authenticator.getCurrentUser()?.uid!!
                val response = campaignApi.getRegisteredCampaigns(userId, 1)
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
                val response = campaignApi.registerCampaign(campaignId)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun submitCampaign(
        campaignId: Int,
        submissionUrl: String
    ): LiveData<ResultState<CampaignSubmissionData>> = liveData {
        try {
            val body = CampaignSubmissionBody(submissionUrl)
            val response = campaignApi.submitCampaign(campaignId, body)
            response.data?.let {
                emit(ResultState.Success(it))
            } ?: emit(ResultState.Error(response.message.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }
}