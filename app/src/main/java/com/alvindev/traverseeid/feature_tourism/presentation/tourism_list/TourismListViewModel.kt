package com.alvindev.traverseeid.feature_tourism.presentation.tourism_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.domain.use_case.UseCasesTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TourismListViewModel @Inject constructor(
    private val useCases: UseCasesTourism,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {
    var state by mutableStateOf(TourismListState())
        private set

    init {
        getTourismLocations()
    }

    fun setLocation(locationName: String) {
        val location = state.tourismLocations.find { it.name == locationName }
        state = state.copy(locationId = location?.id, locationName = location?.name)
    }

    fun getAllTourisms(categoryId: Int?, searchQuery: String?) = useCases.getTourisms(
        TourismParams(
            locationId = state.locationId,
            categoryId = categoryId,
            search = searchQuery
        )
    ).cachedIn(viewModelScope)

    private fun getTourismLocations() = viewModelScope.launch {
        useCases.getTourismLocations().asFlow().collect {
            state = when (it) {
                is ResultState.Success -> {
                    val allLocation = LocationEntity(
                        0,
                        resourcesProvider.getString(R.string.Indonesia)
                    )
                    val list = mutableListOf(allLocation)
                    list.addAll(it.data)
                    state.copy(tourismLocations = list)
                }
                else -> state
            }
        }
    }
}
