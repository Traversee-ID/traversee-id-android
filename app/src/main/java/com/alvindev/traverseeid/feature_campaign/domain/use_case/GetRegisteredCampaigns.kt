package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class GetRegisteredCampaigns(
    private val campaignRepository: CampaignRepository,
) {
    operator fun invoke() = campaignRepository.getRegisteredCampaigns()
}