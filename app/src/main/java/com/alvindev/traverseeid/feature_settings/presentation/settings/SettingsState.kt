package com.alvindev.traverseeid.feature_settings.presentation.settings

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class SettingsState(
    val firebaseUser: FirebaseUser? = Firebase.auth.currentUser,
    val isLogout: Boolean = false,
    val selectedImagePicker: Uri? = null,
    val isShowDialog: Boolean = false,
)