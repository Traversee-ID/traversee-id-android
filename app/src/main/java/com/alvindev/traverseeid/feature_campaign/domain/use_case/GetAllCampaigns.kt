package com.alvindev.traverseeid.feature_campaign.domain.use_case

import androidx.paging.PagingData
import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow

class GetAllCampaigns(
    private val repository: CampaignRepository
) {
    operator fun invoke(status:String? = null): Flow<PagingData<CampaignItem>> = repository.getAllCampaigns(status)
}