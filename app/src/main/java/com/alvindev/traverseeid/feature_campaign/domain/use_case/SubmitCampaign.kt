package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class SubmitCampaign(
    private val repository: CampaignRepository
) {
    suspend operator fun invoke(campaignId: Int, submissionUrl: String) =
        repository.submitCampaign(campaignId, submissionUrl)
}