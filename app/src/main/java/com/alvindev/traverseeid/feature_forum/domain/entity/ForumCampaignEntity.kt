package com.alvindev.traverseeid.feature_forum.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumCampaignEntity(
    val id: Int,
    val name: String,
    val category: String,
    val imageUrl: String? = null,
) : Parcelable
