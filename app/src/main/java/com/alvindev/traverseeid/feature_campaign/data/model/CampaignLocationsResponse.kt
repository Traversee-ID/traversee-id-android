package com.alvindev.traverseeid.feature_campaign.data.model

import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignLocationEntity
import com.google.gson.annotations.SerializedName

data class CampaignLocationsResponse(
	@field:SerializedName("data")
	val data: List<CampaignLocationEntity>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)
