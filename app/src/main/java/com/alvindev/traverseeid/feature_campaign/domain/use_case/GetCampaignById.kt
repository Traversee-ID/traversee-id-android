package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class GetCampaignById(
    private val repository: CampaignRepository
) {
    suspend operator fun invoke(id: Int) = repository.getCampaignById(id)
}