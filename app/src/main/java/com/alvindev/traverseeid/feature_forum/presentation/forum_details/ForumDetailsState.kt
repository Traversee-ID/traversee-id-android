package com.alvindev.traverseeid.feature_forum.presentation.forum_details

data class ForumDetailsState(
    val error: String? = null,
    val isSuccess: Boolean = false,
    val isSubmitting: Boolean = false,
    val text: String = "",
)
