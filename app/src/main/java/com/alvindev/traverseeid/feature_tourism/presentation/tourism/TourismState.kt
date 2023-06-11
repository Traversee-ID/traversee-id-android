package com.alvindev.traverseeid.feature_tourism.presentation.tourism

import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity

data class TourismState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val tourismCategories: List<CategoryEntity>? = null,
    val openTrips: List<TripEntity>? = null,
    val tourismRecommendations: List<TourismItem>? = null,
)
