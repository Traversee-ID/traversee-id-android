package com.alvindev.traverseeid.feature_auth.presentation.reset

data class ResetState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val email: String = "",
    val isSuccess: Boolean = false,
)