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
import com.alvindev.traverseeid.feature_tourism.data.remote.TourismApi
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismDetailsEntity
import com.alvindev.traverseeid.feature_tourism.domain.entity.TourismItem
import com.alvindev.traverseeid.feature_tourism.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class TourismRepositoryImpl @Inject constructor(
    private val tourismApi: TourismApi
) : TourismRepository {
    override suspend fun getTourismCategories(): LiveData<ResultState<List<CategoryEntity>>> =
        liveData {
            try {
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
                val response = tourismApi.getTourismLocations()
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override fun getTourisms(params: TourismParams): Flow<PagingData<TourismItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                TourismPagingSource(
                    tourismApi,
                    tourismParams = params,
                )
            }
        ).flow
    }

    override suspend fun getTourismDetails(id: Int): LiveData<ResultState<TourismDetailsEntity>> =
        liveData {
            try {
                val response = tourismApi.getTourismDetails(id)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun postFavoriteTourism(id: Int): LiveData<ResultState<TourismItem>>  =
        liveData {
            try {
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

    override suspend fun deleteFavoriteTourism(id: Int): LiveData<ResultState<TourismItem>> =
        liveData {
            try {
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
}