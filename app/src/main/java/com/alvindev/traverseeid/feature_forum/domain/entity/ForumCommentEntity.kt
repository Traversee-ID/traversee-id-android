package com.alvindev.traverseeid.feature_forum.domain.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ForumCommentEntity(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("author_id")
	val authorId: String,

	@field:SerializedName("forum_id")
	val forumId: Int,

	@field:SerializedName("user_display_name")
	val authorName: String? = null,

	@field:SerializedName("user_profile_image")
	val authorProfileImage: String? = null,

	@field:SerializedName("created_date")
	val createdAt: String? = null,
) : Parcelable
