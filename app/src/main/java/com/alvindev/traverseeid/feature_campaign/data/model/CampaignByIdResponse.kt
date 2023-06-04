package com.alvindev.traverseeid.feature_campaign.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.alvindev.traverseeid.feature_campaign.domain.entity.CampaignEntity
import com.google.gson.annotations.SerializedName

data class CampaignByIdResponse(
	@field:SerializedName("data")
	val data: CampaignItem? = null,

	@field:SerializedName("message")
	val message: String? = null
)
