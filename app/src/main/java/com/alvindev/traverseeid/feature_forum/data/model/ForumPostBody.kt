package com.alvindev.traverseeid.feature_forum.data.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class ForumPostBody(
    @field:SerializedName("title")
    val title: RequestBody,
    @field:SerializedName("text")
    val text: RequestBody,
    @field:SerializedName("campaign_id")
    val campaignId: RequestBody? = null,
    @field:SerializedName("image")
    val image: MultipartBody.Part? = null,
)
