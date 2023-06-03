package com.alvindev.traverseeid.feature_tourism.presentation.tourism_list

import com.alvindev.traverseeid.core.domain.entity.LocationEntity

data class TourismListState(
    val categoryId: Int = -1,
    val tourismLocations: List<LocationEntity> = listOf(
        LocationEntity(
            id = 0,
            name = "Indonesia",
        )
    ),
    val locationId: Int? = null,
    val locationName: String? = null,
    val search: String? = null
)
