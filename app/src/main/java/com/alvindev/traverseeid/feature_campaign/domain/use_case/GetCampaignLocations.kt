package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class GetCampaignLocations(
    private val campaignRepository: CampaignRepository
) {
    suspend operator fun invoke() = campaignRepository.getCampaignLocations()
}