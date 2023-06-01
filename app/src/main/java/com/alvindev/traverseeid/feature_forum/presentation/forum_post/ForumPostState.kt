package com.alvindev.traverseeid.feature_forum.presentation.forum_post

import android.net.Uri

data class ForumPostState (
    val title: String = "",
    val text: String = "",
    val selectedImagePicker: Uri? = null,
    val isShowDialog: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
)