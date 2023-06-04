package com.alvindev.traverseeid.feature_tourism.presentation.tourism

import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity

data class TourismState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val tourismCategories: List<CategoryEntity> = emptyList(),
    val openTrips: List<TripEntity>? = null,
)
