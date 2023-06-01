package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class RegisterCampaign(
    private val campaignRepository: CampaignRepository,
) {
    suspend operator fun invoke(campaignId: Int) = campaignRepository.registerCampaign(campaignId)
}
