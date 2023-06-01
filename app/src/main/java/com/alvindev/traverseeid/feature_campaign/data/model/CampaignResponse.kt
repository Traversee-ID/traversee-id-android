package com.alvindev.traverseeid.feature_campaign.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignEntity
import com.google.gson.annotations.SerializedName

data class CampaignResponse(
	@field:SerializedName("data")
	val data: List<CampaignItem>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class CampaignItem(
	@field:SerializedName("is_registered")
	val isRegistered: Boolean,

	@field:SerializedName("campaign")
	val campaign: CampaignEntity
) : Parcelable
