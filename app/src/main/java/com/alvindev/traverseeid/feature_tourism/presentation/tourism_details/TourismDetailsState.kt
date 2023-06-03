package com.alvindev.traverseeid.feature_tourism.presentation.tourism_details

import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismDetailsEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismEntity

data class TourismDetailsState(
    val tourism: TourismEntity? = null,
    val tourismDetails: TourismDetailsEntity? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val isFavorite: Boolean = false,
    val isLoadingFavorite: Boolean = false,
    val errorFavorite : String? = null
)
