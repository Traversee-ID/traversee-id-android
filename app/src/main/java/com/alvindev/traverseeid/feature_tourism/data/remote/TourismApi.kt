package com.alvindev.traverseeid.feature_tourism.data.remote

import com.alvindev.traverseeid.core.data.model.CategoriesResponse
import com.alvindev.traverseeid.core.data.model.LocationsResponse
import com.alvindev.traverseeid.feature_tourism.data.model.*
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
        @Query("location_id") locationId: Int? = null,
        @Query("search") search: String? = null,
    ): TourismResponse

    @GET("tourism-recommendations")
    suspend fun getTourismRecommendations(): TourismResponse

    @GET("tourisms/{id}")
    suspend fun getTourismById(
        @Path("id") id: String
    ): TourismByIdResponse

    @GET("tourisms/{id}/details")
    suspend fun getTourismDetails(
        @Path("id") id: String
    ): TourismDetailsResponse

    @POST("tourisms/{id}/favorites")
    suspend fun postFavoriteTourism(
        @Path("id") id: String
    ): Response<TourismFavoriteResponse>

    @DELETE("tourisms/{id}/favorites")
    suspend fun deleteFavoriteTourism(
        @Path("id") id: String
    ): Response<TourismFavoriteResponse>

    @GET("open-trips")
    suspend fun getOpenTrip(
        @Query("page") page: Int,
    ): TripResponse

    @GET("open-trips/{id}/destinations")
    suspend fun getTripDestinations(
        @Path("id") id: Int
    ): TripDestinationResponse
}