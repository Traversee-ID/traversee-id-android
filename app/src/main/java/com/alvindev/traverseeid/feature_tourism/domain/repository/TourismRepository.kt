package com.alvindev.traverseeid.feature_tourism.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismDetailsEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity
import kotlinx.coroutines.flow.Flow

interface TourismRepository {
    suspend fun getTourismCategories(): LiveData<ResultState<List<CategoryEntity>>>

    suspend fun getTourismLocations(): LiveData<ResultState<List<LocationEntity>>>

    fun getTourisms(params: TourismParams): Flow<PagingData<TourismItem>>

    suspend fun getTourismRecommendations(): LiveData<ResultState<List<TourismItem>>>

    suspend fun getTourismById(id: String): LiveData<ResultState<TourismItem>>

    suspend fun getTourismDetails(id: String): LiveData<ResultState<TourismDetailsEntity>>

    suspend fun postFavoriteTourism(id: String): LiveData<ResultState<TourismItem>>

    suspend fun deleteFavoriteTourism(id: String): LiveData<ResultState<TourismItem>>
    fun getOpenTrip(): Flow<PagingData<TripEntity>>

    suspend fun getFirstPageOpenTrip(): LiveData<ResultState<List<TripEntity>>>

    suspend fun getTripDestinations(id: Int): LiveData<ResultState<List<TourismEntity>>>

}