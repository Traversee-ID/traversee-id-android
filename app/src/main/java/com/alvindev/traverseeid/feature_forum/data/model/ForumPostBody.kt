package com.alvindev.traverseeid.feature_forum.data.model

import com.google.gson.annotations.SerializedName

data class ForumPostBody(
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("text")
    val text: String,
    @field:SerializedName("campaign_id")
    val campaignId: Int? = null,
)
