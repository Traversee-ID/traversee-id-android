package com.alvindev.traverseeid.feature_tourism.presentation.trip_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.feature_tourism.domain.use_case.UseCasesTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripListViewModel @Inject constructor(
    private val useCases: UseCasesTourism
):ViewModel() {

    fun getOpenTrip() = useCases.getOpenTrip().cachedIn(viewModelScope)
}