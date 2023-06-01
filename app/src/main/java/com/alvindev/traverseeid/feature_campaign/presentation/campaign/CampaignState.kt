package com.alvindev.traverseeid.feature_campaign.presentation.campaign

import com.alvindev.traverseeid.feature_campaign.data.model.CampaignItem
import com.alvindev.traverseeid.feature_campaign.domain.entity.CategoryEntity

data class CampaignState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val categories: List<CategoryEntity>? = null,
    val myCampaigns: List<CampaignItem>? = null,
)
