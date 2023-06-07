package com.alvindev.traverseeid.di

import com.alvindev.traverseeid.core.data.local.UserDataStoreRepositoryImpl
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_settings.data.remote.SettingsApi
import com.alvindev.traverseeid.feature_settings.data.repository.SettingsRepositoryImpl
import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository
import com.alvindev.traverseeid.feature_settings.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {
    @Singleton
    @Provides
    fun provideSettingsRepository(authenticator: BaseAuthRepository, dataStore: UserDataStoreRepositoryImpl, settingsApi: SettingsApi): SettingsRepository {
        return SettingsRepositoryImpl(authenticator, dataStore, settingsApi)
    }

    @Provides
    @Singleton
    fun provideSettingsApi(): SettingsApi {
        return ApiConfig.getApiService(SettingsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSettingsUseCases(repository: SettingsRepository): UseCasesSettings {
        return UseCasesSettings(
            logout = Logout(repository),
            changeLanguage = ChangeLanguage(repository),
            updateProfile = UpdateProfile(repository),
            updateProfilePicture = UpdateProfilePicture(repository)
        )
    }
}