package com.alvindev.traverseeid.feature_campaign.presentation.campaign_list

import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignLocationEntity

data class CampaignListState(
    val status: String? = null,
    val categoryId: Int = -1,
    val campaignLocations: List<CampaignLocationEntity> = listOf(
        CampaignLocationEntity(
            id = 0,
            name = "Indonesia",
        )
    ),
    val locationId: Int? = null,
    val isRegistered: Boolean? = null,
)
