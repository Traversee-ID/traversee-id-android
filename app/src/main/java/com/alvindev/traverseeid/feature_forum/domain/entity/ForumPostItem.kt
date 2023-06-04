package com.alvindev.traverseeid.feature_forum.domain.entity

import android.os.Parcelable
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumPostItem(
    @field:SerializedName("forum")
    val forum: ForumPostEntity,

    @field:SerializedName("is_liked")
    val isLiked: Boolean,

    @field:SerializedName("campaign")
    val campaign: ForumCampaignEntity? = null,
) : Parcelable
