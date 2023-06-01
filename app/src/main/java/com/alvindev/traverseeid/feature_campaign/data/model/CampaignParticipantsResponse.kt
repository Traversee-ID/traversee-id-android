package com.alvindev.traverseeid.feature_campaign.data.model

import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignParticipantEntity
import com.google.gson.annotations.SerializedName

data class CampaignParticipantsResponse(
	@field:SerializedName("data")
	val data: List<CampaignParticipantsItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

data class CampaignParticipantsItem(
	@field:SerializedName("other_participants")
	val otherParticipants: List<CampaignParticipantEntity>? = null,

	@field:SerializedName("winners")
	val winners: List<CampaignParticipantEntity>? = null,
)
