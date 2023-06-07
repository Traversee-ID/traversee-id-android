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
            isLoading = true,
            tourism = tourismItem.tourism,
            isFavorite = tourismItem.isFavorite
        )
        getTourismDetails(tourismItem.tourism.id)
    }

    fun setInitialStateWithId(id: String) {
        getTourismById(id)
    }

    private fun getTourismDetails(id: String) = viewModelScope.launch {
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

    private fun getTourismById(id: String) = viewModelScope.launch {
        useCases.getTourismById(id).asFlow().collect {
            when (it) {
                is ResultState.Loading -> state = state.copy(
                    isLoading = true,
                    error = null,
                    tourism = null
                )
                is ResultState.Success -> {
                    setInitialState(it.data)
                }
                is ResultState.Error -> state = state.copy(
                    isLoading = false,
                    error = it.error,
                    tourism = null
                )
            }
        }
    }

    fun addFavoriteTourism(id: String) = viewModelScope.launch {
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

    fun removeFavoriteTourism(id: String) = viewModelScope.launch {
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
