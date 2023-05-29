package com.alvindev.traverseeid.di

import com.alvindev.traverseeid.core.data.local.UserDataStoreRepositoryImpl
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_settings.data.repository.SettingsRepositoryImpl
import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository
import com.alvindev.traverseeid.feature_settings.domain.use_case.ChangeLanguage
import com.alvindev.traverseeid.feature_settings.domain.use_case.Logout
import com.alvindev.traverseeid.feature_settings.domain.use_case.UpdateProfile
import com.alvindev.traverseeid.feature_settings.domain.use_case.UseCasesSettings
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
    fun provideSettingsRepository(authenticator: BaseAuthRepository, dataStore: UserDataStoreRepositoryImpl): SettingsRepository {
        return SettingsRepositoryImpl(authenticator, dataStore)
    }
    @Singleton
    @Provides
    fun provideSettingsUseCases(repository: SettingsRepository): UseCasesSettings {
        return UseCasesSettings(
            logout = Logout(repository),
            changeLanguage = ChangeLanguage(repository),
            updateProfile = UpdateProfile(repository),
        )
    }
}