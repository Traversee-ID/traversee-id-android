package com.alvindev.traverseeid.feature_campaign.domain.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CampaignParticipantEntity(
	@field:SerializedName("submission_url")
	val submissionUrl: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("user_display_name")
	val userDisplayName: String? = null,

	@field:SerializedName("user_profile_image")
	val userProfileImage: String? = null,

	@field:SerializedName("position")
	val position: Int? = null
) : Parcelable
