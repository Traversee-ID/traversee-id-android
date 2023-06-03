package com.alvindev.traverseeid.feature_tourism.presentation.tourism_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.feature_tourism.domain.use_case.UseCasesTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourismDetailsViewModel @Inject constructor(
    private val useCases: UseCasesTourism
) : ViewModel() {
    var state by mutableStateOf(TourismDetailsState())
        private set

    fun setInitialState(tourismItem: TourismItem) {
        state = state.copy(
            tourism = tourismItem.tourism,
            isFavorite = tourismItem.isFavorite
        )
        getTourismDetails(tourismItem.tourism.id)
    }

    private fun getTourismDetails(id: Int) = viewModelScope.launch {
        useCases.getTourismDetails(id).asFlow().collect {
            state = when (it) {
                is ResultState.Loading -> state.copy(
                    isLoading = true,
                    error = null,
                    tourismDetails = null
                )
                is ResultState.Success -> state.copy(
                    isLoading = false,
                    tourismDetails = it.data,
                    error = null
                )
                is ResultState.Error -> state.copy(
                    isLoading = false,
                    error = it.error,
                    tourismDetails = null
                )
            }
        }
    }

    fun addFavoriteTourism(id: Int) = viewModelScope.launch {
        state = state.copy(errorFavorite = null, isLoadingFavorite = true)

        useCases.postFavoriteTourism(id).asFlow().collect {
            state = when (it) {
                is ResultState.Error -> {
                    state.copy(
                        isLoadingFavorite = false,
                        errorFavorite = it.error
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        isLoadingFavorite = false,
                        errorFavorite = null,
                        isFavorite = true
                    )
                }
                is ResultState.Loading -> {
                    state.copy(
                        isLoadingFavorite = true,
                        errorFavorite = null,
                    )
                }
            }
        }
    }

    fun removeFavoriteTourism(id: Int) = viewModelScope.launch {
        state = state.copy(errorFavorite = null, isLoadingFavorite = true)

        useCases.deleteFavoriteTourism(id).asFlow().collect {
            state = when (it) {
                is ResultState.Error -> {
                    state.copy(
                        isLoadingFavorite = false,
                        errorFavorite = it.error
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        isLoadingFavorite = false,
                        errorFavorite = null,
                        isFavorite = false
                    )
                }
                is ResultState.Loading -> {
                    state.copy(
                        isLoadingFavorite = true,
                        errorFavorite = null,
                    )
                }
            }
        }
    }
}
