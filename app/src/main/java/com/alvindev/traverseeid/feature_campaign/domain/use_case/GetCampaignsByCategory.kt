package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.data.model.CampaignParams
import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class GetCampaignsByCategory(
    private val repository: CampaignRepository
) {
    operator fun invoke(categoryId: Int, params: CampaignParams) =
        repository.getCampaignsByCategory(categoryId, params)
}