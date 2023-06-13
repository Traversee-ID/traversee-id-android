package com.alvindev.traverseeid.feature_tourism.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.domain.entity.CategoryEntity
import com.alvindev.traverseeid.core.domain.entity.LocationEntity
import com.alvindev.traverseeid.feature_tourism.data.model.TourismParams
import com.alvindev.traverseeid.feature_tourism.data.paging_soruce.TourismPagingSource
import com.alvindev.traverseeid.feature_tourism.data.paging_soruce.TripPagingSource
import com.alvindev.traverseeid.feature_tourism.data.remote.TourismApi
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismDetailsEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.feature_tourism.domain.entity.TripEntity
import com.alvindev.traverseeid.feature_tourism.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class TourismRepositoryImpl @Inject constructor(
    private val tourismApi: TourismApi
) : TourismRepository {
    override suspend fun getTourismCategories(): LiveData<ResultState<List<CategoryEntity>>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = tourismApi.getTourismCategories()
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getTourismLocations(): LiveData<ResultState<List<LocationEntity>>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = tourismApi.getTourismLocations()
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override fun getTourisms(page: Int, params: TourismParams): Flow<ResultState<List<TourismItem>>> = flow{
        try {
            emit(ResultState.Loading)
            val response = tourismApi.getTourism(
                page,
                categoryId = params.categoryId,
                isFavorite = params.isFavorite,
                locationId = params.locationId,
                search = params.search,
            )
            response.data?.let {
                emit(ResultState.Success(it))
            } ?: emit(ResultState.Error(response.message.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun getTourismRecommendations(): LiveData<ResultState<List<TourismItem>>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = tourismApi.getTourismRecommendations()
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getTourismById(id: String): LiveData<ResultState<TourismItem>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = tourismApi.getTourismById(id)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getTourismDetails(id: String): LiveData<ResultState<TourismDetailsEntity>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = tourismApi.getTourismDetails(id)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun postFavoriteTourism(id: String): LiveData<ResultState<TourismItem>>  =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = tourismApi.postFavoriteTourism(id)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "Error: ${response.code()} ${response.message()}"
                    }
                    emit(ResultState.Error(errorMessage))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: e.message.toString()))
            }
        }

    override suspend fun deleteFavoriteTourism(id: String): LiveData<ResultState<TourismItem>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = tourismApi.deleteFavoriteTourism(id)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "Error: ${response.code()} ${response.message()}"
                    }
                    emit(ResultState.Error(errorMessage))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: e.message.toString()))
            }
        }

    override fun getOpenTrip(): Flow<PagingData<TripEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                TripPagingSource(
                    tourismApi,
                )
            }
        ).flow
    }

    override suspend fun getFirstPageOpenTrip(): LiveData<ResultState<List<TripEntity>>> =
        liveData {
            try {
                val response = tourismApi.getOpenTrip(1)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun getTripDestinations(id: Int): LiveData<ResultState<List<TourismEntity>>> =
        liveData {
            try {
                val response = tourismApi.getTripDestinations(id)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }
}