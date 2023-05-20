package com.alvindev.traverseeid.feature_auth.presentation.register

import com.alvindev.traverseeid.feature_auth.domain.entity.User

data class RegisterState(
    val firebaseUser: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passswordConfirmation: String = "",
    val isPasswordConfirmationVisible: Boolean = false,
    val isSubmitting: Boolean = false,
)