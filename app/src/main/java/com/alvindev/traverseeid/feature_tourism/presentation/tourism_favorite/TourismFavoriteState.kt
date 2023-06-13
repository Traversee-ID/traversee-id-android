package com.alvindev.traverseeid.feature_tourism.presentation.tourism_favorite

import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem

data class TourismFavoriteState(
    val tourisms: List<TourismItem> = emptyList(),
    val page : Int = 1,
    val canPaginate : Boolean = true,
    val listState: ListState = ListState.IDLE,
    val error: String? = null,
    val lastSeenIndex: Int = 0,
)
