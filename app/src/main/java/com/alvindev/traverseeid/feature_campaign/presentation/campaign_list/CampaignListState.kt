package com.alvindev.traverseeid.feature_campaign.presentation.campaign_list

import com.alvindev.traverseeid.core.domain.entity.LocationEntity

data class CampaignListState(
    val status: String? = null,
    val campaignLocations: List<LocationEntity> = listOf(
        LocationEntity(
            id = 0,
            name = "Indonesia",
        )
    ),
    val locationId: Int? = null,
    val isRegistered: Boolean? = null,
)
