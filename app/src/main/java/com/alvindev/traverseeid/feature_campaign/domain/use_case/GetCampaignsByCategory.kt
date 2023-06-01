package com.alvindev.traverseeid.feature_campaign.domain.use_case

import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository

class GetCampaignsByCategory(
    private val repository: CampaignRepository
) {
    operator fun invoke(categoryId: Int, status: String? = null, locationId: Int? = null) =
        repository.getCampaignsByCategory(categoryId, status, locationId)
}