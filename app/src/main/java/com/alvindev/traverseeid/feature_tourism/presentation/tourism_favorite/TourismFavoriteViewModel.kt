package com.alvindev.traverseeid.feature_tourism.presentation.tourism_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.domain.use_case.UseCasesTourism
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TourismFavoriteViewModel @Inject constructor(
    private val useCases: UseCasesTourism,
) : ViewModel() {

    fun getFavoriteTourisms() = useCases.getTourisms(
        TourismParams(
            isFavorite = true,
        )
    ).cachedIn(viewModelScope)
}
