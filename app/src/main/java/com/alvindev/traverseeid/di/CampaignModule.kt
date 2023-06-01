package com.alvindev.traverseeid.di

import ApiConfig
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_campaign.data.remote.CampaignApi
import com.alvindev.traverseeid.feature_campaign.data.repository.CampaignRepositoryImpl
import com.alvindev.traverseeid.feature_campaign.domain.repository.CampaignRepository
import com.alvindev.traverseeid.feature_campaign.domain.use_case.*
import com.google.firebase.auth.FirebaseAuth
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
    fun provideCampaignRepository(campaignApi: CampaignApi, authenticator: BaseAuthRepository): CampaignRepository {
        return CampaignRepositoryImpl(campaignApi, authenticator)
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
            getCategories = GetCategories(repository),
            getAllCampaigns = GetAllCampaigns(repository),
            getCampaignsByCategory = GetCampaignsByCategory(repository),
            getRegisteredCampaigns = GetRegisteredCampaigns(repository),
            getFirstPageRegisteredCampaigns = GetFirstPageRegisteredCampaigns(repository),
            getCampaignDetails = GetCampaignDetails(repository),
            getCampaignParticipants = GetCampaignParticipants(repository),
            registerCampaign = RegisterCampaign(repository),
            submitCampaign = SubmitCampaign(repository),
            getCampaignLocations = GetCampaignLocations(repository)
        )
    }
}