package com.alvindev.traverseeid.feature_tourism.presentation.trip_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity
import com.alvindev.traverseeid.feature_tourism.domain.use_case.UseCasesTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val useCases: UseCasesTourism,
): ViewModel() {
    var state by mutableStateOf(TripDetailsState())
        private set

    fun setInitialData(trip: TripEntity){
        state = state.copy(
            trip = trip,
        )
        getTripDestinations(trip.id)
    }

    private fun getTripDestinations(id: Int) = viewModelScope.launch {
        useCases.getTripDestinations(id).asFlow().collect{
            state = when(it){
                is ResultState.Loading -> {
                    state.copy(
                        isLoading = true,
                        error = null,
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        isLoading = false,
                        destinations = it.data,
                        error = null,
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        isLoading = false,
                        error = it.error,
                    )
                }
            }
        }
    }
}