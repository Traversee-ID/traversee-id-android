package com.alvindev.traverseeid.feature_forum.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumCampaignEntity(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,
) : Parcelable
