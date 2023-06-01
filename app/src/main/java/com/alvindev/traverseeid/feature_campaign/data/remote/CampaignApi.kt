package com.alvindev.traverseeid.feature_campaign.data.remote

import com.alvindev.traverseeid.feature_campaign.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CampaignApi {
    @GET("campaign-categories")
    suspend fun getCategories(): CategoriesResponse

    @GET("campaigns")
    suspend fun getAllCampaigns(
        @Query("page") page: Int,
        @Query("status") status: String? = null,
        @Query("location_id") locationId: Int? = null
    ): CampaignResponse

    @GET("campaign-categories/{id}/campaigns")
    suspend fun getCampaignsByCategory(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("status") status: String? = null,
        @Query("location_id") locationId: Int? = null
    ): CampaignResponse

    @GET("/users/{id}/campaigns")
    suspend fun getRegisteredCampaigns(
        @Path("id") id: String,
        @Query("page") page: Int,
    ): CampaignResponse

    @GET("campaigns/{id}/details")
    suspend fun getCampaignDetails(@Path("id") id: Int): CampaignDetailsResponse

    @GET("campaigns/{id}/participants")
    suspend fun getCampaignParticipants(@Path("id") id: Int): CampaignParticipantsResponse

    @POST("campaigns/{id}/registrations")
    suspend fun registerCampaign(@Path("id") id: Int): Response<CampaignRegisterResponse>

    @POST("campaigns/{id}/submissions")
    suspend fun submitCampaign(
        @Path("id") id: Int,
        @Body body: CampaignSubmissionBody
    ): Response<CampaignSubmissionResponse>

    @GET("campaign-locations")
    suspend fun getCampaignLocations(): CampaignLocationsResponse
}