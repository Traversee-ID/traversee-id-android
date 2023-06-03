package com.alvindev.traverseeid.feature_tourism.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismDetailsEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import kotlinx.coroutines.flow.Flow

interface TourismRepository {
    suspend fun getTourismCategories(): LiveData<ResultState<List<CategoryEntity>>>

    suspend fun getTourismLocations(): LiveData<ResultState<List<LocationEntity>>>

    fun getTourisms(params: TourismParams): Flow<PagingData<TourismItem>>

    suspend fun getTourismDetails(id: Int): LiveData<ResultState<TourismDetailsEntity>>

    suspend fun postFavoriteTourism(id: Int): LiveData<ResultState<TourismItem>>

    suspend fun deleteFavoriteTourism(id: Int): LiveData<ResultState<TourismItem>>
}