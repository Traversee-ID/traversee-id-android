package com.alvindev.traverseeid.feature_campaign.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CampaignLocationEntity(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,
): Parcelable
