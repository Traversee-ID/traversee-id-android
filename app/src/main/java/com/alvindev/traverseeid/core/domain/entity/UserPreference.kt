package com.alvindev.traverseeid.core.domain.entity

import android.net.Uri

data class UserPreference(
    val name: String? = null,
    val email: String? = null,
    val avatarUrl: Uri? = null,
    val isDarkMode: Boolean? = null,
    val language: String? = null,
    val uid: String? = null,
    val token: String? = null,
)
