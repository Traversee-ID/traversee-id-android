package com.alvindev.traverseeid.feature_settings.presentation.language

data class LanguageState(
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
