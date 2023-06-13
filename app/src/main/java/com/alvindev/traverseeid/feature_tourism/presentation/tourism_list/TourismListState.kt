package com.alvindev.traverseeid.feature_tourism.presentation.tourism_list

import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem

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
    val search: String? = null,
    val tourisms: List<TourismItem> = emptyList(),
    val page : Int = 1,
    val canPaginate : Boolean = true,
    val listState: ListState = ListState.IDLE,
    val error: String? = null,
    val lastSeenIndex: Int = 0,
)
