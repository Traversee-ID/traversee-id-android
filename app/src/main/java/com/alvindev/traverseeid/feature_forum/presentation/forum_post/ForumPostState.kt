package com.alvindev.traverseeid.feature_forum.presentation.forum_post

import android.net.Uri

data class ForumPostState (
    val selectedImagePicker: Uri? = null,
    val isShowDialog: Boolean = false,
    val isSubmitting: Boolean = false,
)