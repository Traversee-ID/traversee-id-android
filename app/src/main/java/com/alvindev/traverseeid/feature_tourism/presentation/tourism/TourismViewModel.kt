package com.alvindev.traverseeid.feature_tourism.presentation.tourism

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.feature_tourism.domain.use_case.UseCasesTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourismViewModel @Inject constructor(
    private val useCases: UseCasesTourism,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    var state by mutableStateOf(TourismState())
        private set

    init {
        getTourismCategories()
    }

    fun getTourismCategories() = viewModelScope.launch {
        useCases.getTourismCategories().asFlow().collect {
            when (it) {
                is ResultState.Loading -> {
                    state = state.copy(
                        error = null
                    )
                }
                is ResultState.Success -> {
                    val allTourism = CategoryEntity(
                        id = -1,
                        name = resourcesProvider.getString(R.string.all_tourism),
                        imageUrl = null,
                    )

                    val categories = it.data.toMutableList()
                    categories.add(0, allTourism)

                    state = state.copy(
                        error = null,
                        tourismCategories = categories,
                    )

                    getOpenTrips()
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = it.error,
                    )
                }
            }
        }
    }

    private fun getOpenTrips() = viewModelScope.launch {
        useCases.getFirstPageOpenTrip().asFlow().collect {
            when (it) {
                is ResultState.Loading -> {
                    state = state.copy(
                        error = null
                    )
                }
                is ResultState.Success -> {
                    state = state.copy(
                        error = null,
                        openTrips = it.data
                    )

                    getTourismRecommendations()
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = it.error,
                    )
                }
            }
        }
    }

    private fun getTourismRecommendations() = viewModelScope.launch {
        useCases.getTourismRecommendations().asFlow().collect {
            when (it) {
                is ResultState.Loading -> {
                    state = state.copy(
                        error = null
                    )
                }
                is ResultState.Success -> {
                    state = state.copy(
                        error = null,
                        tourismRecommendations = it.data,
                        isLoading = false,
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }
}