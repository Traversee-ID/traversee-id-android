package com.alvindev.traverseeid.feature_tourism.data.remote

import com.alvindev.traverseeid.core.data.model.CategoriesResponse
import com.alvindev.traverseeid.core.data.model.LocationsResponse
import com.alvindev.traverseeid.feature_tourism.data.model.TourismDetailsResponse
import com.alvindev.traverseeid.feature_tourism.data.model.TourismFavoriteResponse
import com.alvindev.traverseeid.feature_tourism.data.model.TourismResponse
import retrofit2.Response
import retrofit2.http.*

interface TourismApi {
    @GET("tourism-categories")
    suspend fun getTourismCategories(): CategoriesResponse

    @GET("tourism-locations")
    suspend fun getTourismLocations(): LocationsResponse

    @GET("tourisms")
    suspend fun getTourism(
        @Query("page") page: Int,
        @Query("category_id") categoryId: Int? = null,
        @Query("is_favorite") isFavorite: Boolean? = null,
    ): TourismResponse

    @GET("tourisms/{id}/details")
    suspend fun getTourismDetails(
        @Path("id") id: Int
    ): TourismDetailsResponse

    @POST("tourisms/{id}/favorites")
    suspend fun postFavoriteTourism(
        @Path("id") id: Int
    ): Response<TourismFavoriteResponse>

    @DELETE("tourisms/{id}/favorites")
    suspend fun deleteFavoriteTourism(
        @Path("id") id: Int
    ): Response<TourismFavoriteResponse>
}