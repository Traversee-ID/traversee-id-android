package com.alvindev.traverseeid.feature_tourism.presentation.tourism_favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.domain.use_case.UseCasesTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourismFavoriteViewModel @Inject constructor(
    private val useCases: UseCasesTourism,
) : ViewModel() {
    var state by mutableStateOf(TourismFavoriteState())
        private set

    override fun onCleared() {
        state = state.copy(
            page = 1,
            listState = ListState.IDLE,
            canPaginate = false
        )
        super.onCleared()
    }

    fun getFavoriteTourisms() = viewModelScope.launch {
        if (state.page == 1 || (state.page != 1 && state.canPaginate) && state.listState == ListState.IDLE) {
            state = state.copy(
                listState = if (state.page == 1) ListState.LOADING else ListState.PAGINATING
            )
            val params = TourismParams(
                isFavorite = true
            )
            useCases.getTourisms(state.page, params).collect {
                when (it) {
                    is ResultState.Error -> {
                        state = state.copy(
                            error = it.error,
                            listState = if (state.page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST,
                        )
                    }
                    is ResultState.Success -> {
                        state = state.copy(
                            tourisms = if (state.page == 1) it.data else state.tourisms + it.data,
                            page = state.page + 1,
                            canPaginate = it.data.size >= 5,
                            listState = ListState.IDLE,
                        )
                    }
                    else -> {
                        state = state.copy(
                            error = null,
                            listState = if(state.page == 1) ListState.LOADING else ListState.PAGINATING,
                        )
                    }
                }
            }
        }
    }

    fun deleteFavoriteTourism(index: Int) = viewModelScope.launch {
        state = state.copy(
            tourisms = state.tourisms.filterIndexed { i, _ -> i != index },
        )
    }

    fun setPage(page: Int) {
        state = state.copy(
            page = page,
        )
    }
    fun setLastSeenIndex(index: Int) {
        state = state.copy(lastSeenIndex = index)
    }
}
