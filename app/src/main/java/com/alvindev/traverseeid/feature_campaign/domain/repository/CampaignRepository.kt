package com.alvindev.traverseeid.feature_campaign.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_campaign.data.model.*
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {
    suspend fun getCategories(): LiveData<ResultState<List<CategoryEntity>>>

    fun getAllCampaigns(params: CampaignParams): Flow<PagingData<CampaignItem>>

    suspend fun getCampaignById(campaignId: Int): LiveData<ResultState<CampaignItem>>

    fun getCampaignsByCategory(categoryId: Int, params: CampaignParams): Flow<PagingData<CampaignItem>>

    suspend fun getFirstPageRegisteredCampaigns(): LiveData<ResultState<List<CampaignItem>>>

    suspend fun getCampaignDetails(campaignId: Int): LiveData<ResultState<CampaignDetailsData>>

    suspend fun getCampaignParticipants(campaignId: Int): LiveData<ResultState<List<CampaignParticipantsItem>>>

    suspend fun registerCampaign(campaignId: Int): LiveData<ResultState<CampaignRegisterData>>

    suspend fun submitCampaign(campaignId: Int, submissionUrl: String): LiveData<ResultState<CampaignSubmissionData>>

    suspend fun getCampaignLocations(): LiveData<ResultState<List<LocationEntity>>>
}