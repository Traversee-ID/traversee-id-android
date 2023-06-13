package com.alvindev.traverseeid.di

import ApiConfig
import com.alvindev.traverseeid.feature_campaign.domain.use_case.GetCampaignCategories
import com.alvindev.traverseeid.feature_campaign.data.remote.CampaignApi
import com.alvindev.traverseeid.feature_campaign.data.repository.CampaignRepositoryImpl
import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository
import com.alvindev.traverseeid.feature_campaign.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CampaignModule {
    @Provides
    @Singleton
    fun provideCampaignRepository(campaignApi: CampaignApi): CampaignRepository {
        return CampaignRepositoryImpl(campaignApi)
    }

    @Provides
    @Singleton
    fun provideCampaignApi(): CampaignApi {
        return ApiConfig.getApiService(CampaignApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCampaignUseCases(repository: CampaignRepository): UseCasesCampaign {
        return UseCasesCampaign(
            getCampaignCategories = GetCampaignCategories(repository),
            getAllCampaigns = GetAllCampaigns(repository),
            getCampaignById = GetCampaignById(repository),
            getFirstPageRegisteredCampaigns = GetFirstPageRegisteredCampaigns(repository),
            getCampaignDetails = GetCampaignDetails(repository),
            getCampaignParticipants = GetCampaignParticipants(repository),
            registerCampaign = RegisterCampaign(repository),
            submitCampaign = SubmitCampaign(repository),
            getCampaignLocations = GetCampaignLocations(repository)
        )
    }
}