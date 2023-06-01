package com.alvindev.traverseeid.feature_campaign.data.model
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignDetailsEntity
import com.google.gson.annotations.SerializedName

data class CampaignDetailsResponse(
	@field:SerializedName("data")
	val data: CampaignDetailsData? = null,

	@field:SerializedName("message")
	val message: String? = null,
)

data class CampaignDetailsData(
	@field:SerializedName("campaign_detail")
	val campaignDetail: CampaignDetailsEntity,

	@field:SerializedName("submission_url")
	val submissionUrl: String? = null
)

