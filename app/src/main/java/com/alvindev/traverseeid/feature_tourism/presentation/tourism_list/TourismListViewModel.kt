package com.alvindev.traverseeid.feature_tourism.presentation.tourism_list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.common.ListState
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

    override fun onCleared() {
        state = state.copy(
            page = 1,
            listState = ListState.IDLE,
            canPaginate = false
        )
        super.onCleared()
    }

    init {
        getTourismLocations()
    }

    fun setLocation(locationName: String) {
        val location = state.tourismLocations.find { it.name == locationName }
        location?.let{
            state = state.copy(locationId = if(it.id == -1) null else it.id, locationName = it.name, page = 1, canPaginate = false)
            getAllTourisms(state.categoryId, state.search)
        }
    }

    fun getAllTourisms(categoryId: Int?, searchQuery: String?) = viewModelScope.launch {
        if (state.page == 1 || (state.page != 1 && state.canPaginate) && state.listState == ListState.IDLE) {
            state = state.copy(
                listState = if (state.page == 1) ListState.LOADING else ListState.PAGINATING,
                tourisms = if (state.page == 1) listOf() else state.tourisms,
            )
            val params = TourismParams(
                categoryId = if(categoryId == -1) null else categoryId,
                locationId = state.locationId,
                search = searchQuery,
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
                            categoryId = categoryId ?: -1,
                            search = searchQuery,
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

    private fun getTourismLocations() = viewModelScope.launch {
        useCases.getTourismLocations().asFlow().collect {
            state = when (it) {
                is ResultState.Success -> {
                    val allLocation = LocationEntity(
                        -1,
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

    fun setPage(page: Int) {
        state = state.copy(page = page)
    }

    fun setLastSeenIndex(index: Int) {
        state = state.copy(lastSeenIndex = index)
    }
}
