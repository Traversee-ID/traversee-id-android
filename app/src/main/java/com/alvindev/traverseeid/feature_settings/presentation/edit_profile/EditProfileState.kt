package com.alvindev.traverseeid.feature_settings.presentation.edit_profile
import android.net.Uri

data class EditProfileState(
    val name: String = "",
    val email: String = "",
    val avatarUrl: Uri? = null,
    val selectedImagePicker: Uri? = null,
    val isShowDialog: Boolean = false,
    val isSubmitting: Boolean = false,
)
