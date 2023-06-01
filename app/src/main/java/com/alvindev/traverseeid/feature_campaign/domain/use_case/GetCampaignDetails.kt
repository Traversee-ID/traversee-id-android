package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class GetCampaignDetails(
    private val campaignRepository: CampaignRepository
) {
    suspend operator fun invoke(campaignId: Int) = campaignRepository.getCampaignDetails(campaignId)
}