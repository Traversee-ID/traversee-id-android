package com.alvindev.traverseeid.feature_tourism.presentation.trip_details

import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity

data class TripDetailsState(
    val destinations: List<TourismEntity> = emptyList(),
    val trip: TripEntity? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)
