package com.alvindev.traverseeid.feature_auth.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val photo: String? = null,
    val uid: String? = null
    ) : Parcelable