package com.alvindev.traverseeid.feature_forum.presentation.forum_user
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem

data class ForumUserState(
    val error: String? = null,
    val post: ForumPostItem? = null,
)
