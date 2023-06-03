package com.alvindev.traverseeid.feature_tourism.presentation.tourism

import com.alvindev.traverseeid.core.domain.entity.CategoryEntity

data class TourismState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val tourismCategories: List<CategoryEntity> = emptyList()
)
