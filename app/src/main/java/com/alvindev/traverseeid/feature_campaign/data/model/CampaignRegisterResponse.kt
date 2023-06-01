package com.alvindev.traverseeid.feature_campaign.data.model

import com.google.gson.annotations.SerializedName

data class CampaignRegisterResponse(
	@field:SerializedName("data")
	val data: CampaignRegisterData? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

data class CampaignRegisterData(

	@field:SerializedName("submission_url")
	val submissionUrl: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null
)
