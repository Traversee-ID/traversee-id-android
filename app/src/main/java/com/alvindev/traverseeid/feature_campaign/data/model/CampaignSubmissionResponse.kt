package com.alvindev.traverseeid.feature_campaign.data.model

import com.google.gson.annotations.SerializedName

data class CampaignSubmissionResponse(
	@field:SerializedName("data")
	val data: CampaignSubmissionData? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CampaignSubmissionData(

	@field:SerializedName("submission_url")
	val submissionUrl: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null
)
