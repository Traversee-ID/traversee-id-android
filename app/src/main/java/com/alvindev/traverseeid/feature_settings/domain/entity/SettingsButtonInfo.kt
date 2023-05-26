package com.alvindev.traverseeid.feature_settings.domain.entity

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingsButtonInfo(
    val title: String,
    val icon: ImageVector? = null,
    val onClick: () -> Unit = { },
)
