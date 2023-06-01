package com.alvindev.traverseeid.feature_campaign.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_campaign.data.model.*
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignLocationEntity
import com.alvindev.traverseeid.feature_campaign.domain.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {
    suspend fun getCategories(): LiveData<ResultState<List<CategoryEntity>>>

    fun getAllCampaigns(status: String? = null, locationId: Int? = null): Flow<PagingData<CampaignItem>>

    fun getCampaignsByCategory(categoryId: Int, status: String? = null, locationId: Int? = null): Flow<PagingData<CampaignItem>>

    fun getRegisteredCampaigns(): Flow<PagingData<CampaignItem>>

    suspend fun getFirstPageRegisteredCampaigns(): LiveData<ResultState<List<CampaignItem>>>

    suspend fun getCampaignDetails(campaignId: Int): LiveData<ResultState<CampaignDetailsData>>

    suspend fun getCampaignParticipants(campaignId: Int): LiveData<ResultState<List<CampaignParticipantsItem>>>

    suspend fun registerCampaign(campaignId: Int): LiveData<ResultState<CampaignRegisterData>>

    suspend fun submitCampaign(campaignId: Int, submissionUrl: String): LiveData<ResultState<CampaignSubmissionData>>

    suspend fun getCampaignLocations(): LiveData<ResultState<List<CampaignLocationEntity>>>
}