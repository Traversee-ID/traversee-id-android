package com.alvindev.traverseeid.di

import com.alvindev.traverseeid.feature_campaign.domain.use_case.GetCampaignCategories
import com.alvindev.traverseeid.feature_campaign.domain.use_case.*
import com.alvindev.traverseeid.feature_tourism.data.remote.TourismApi
import com.alvindev.traverseeid.feature_tourism.data.repository.TourismRepositoryImpl
import com.alvindev.traverseeid.feature_tourism.domain.repository.TourismRepository
import com.alvindev.traverseeid.feature_tourism.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TourismModule {
    @Provides
    @Singleton
    fun provideTourismRepository(campaignApi: TourismApi): TourismRepository {
        return TourismRepositoryImpl(campaignApi)
    }

    @Provides
    @Singleton
    fun provideTourismApi(): TourismApi {
        return ApiConfig.getApiService(TourismApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTourismUseCases(repository: TourismRepository): UseCasesTourism {
        return UseCasesTourism(
            getTourismCategories = GetTourismCategories(repository),
            getTourismLocations = GetTourismLocations(repository),
            getTourisms = GetTourisms(repository),
            getTourismById = GetTourismById(repository),
            getTourismDetails = GetTourismDetails(repository),
            postFavoriteTourism = PostFavoriteTourism(repository),
            deleteFavoriteTourism = DeleteFavoriteTourism(repository),
            getOpenTrip = GetOpenTrip(repository),
            getFirstPageOpenTrip = GetFirstPageOpenTrip(repository),
            getTripDestinations = GetTripDestinations(repository),
        )
    }
}