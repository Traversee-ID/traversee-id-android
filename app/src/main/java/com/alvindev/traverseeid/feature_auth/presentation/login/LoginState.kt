package com.alvindev.traverseeid.feature_auth.presentation.login

import com.alvindev.traverseeid.feature_auth.domain.entity.User

data class LoginState(
    val firebaseUser: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isSubmitting: Boolean = false,
)