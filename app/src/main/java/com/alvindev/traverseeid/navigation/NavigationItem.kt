package com.alvindev.traverseeid.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val contentDescription: String
)