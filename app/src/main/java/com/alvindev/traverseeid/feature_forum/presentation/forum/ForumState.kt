package com.alvindev.traverseeid.feature_forum.presentation.forum
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem

data class ForumState(
    val error: String? = null,
    val post: ForumPostItem? = null,
)
