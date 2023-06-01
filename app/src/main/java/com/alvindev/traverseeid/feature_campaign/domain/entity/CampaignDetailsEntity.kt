package com.alvindev.traverseeid.feature_campaign.domain.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CampaignDetailsEntity(
	@field:SerializedName("mission")
	val mission: String? = null,

	@field:SerializedName("terms")
	val terms: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("initiator_name")
	val initiatorName: String? = null
) : Parcelable
