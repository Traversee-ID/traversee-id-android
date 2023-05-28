package com.alvindev.traverseeid.di

import com.alvindev.traverseeid.core.data.local.UserDataStoreRepositoryImpl
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_auth.data.repository.AuthRepositoryImpl
import com.alvindev.traverseeid.feature_auth.domain.repository.AuthRepository
import com.alvindev.traverseeid.feature_auth.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Singleton
    @Provides
    fun provideAuthRepository(authenticator: BaseAuthRepository, dataStore: UserDataStoreRepositoryImpl): AuthRepository {
        return AuthRepositoryImpl(authenticator, dataStore)
    }

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository): UseCasesAuth {
        return UseCasesAuth(
            loginWithEmailPassword = LoginWithEmailPassword(repository),
            registerWithEmailPassword = RegisterWithEmailPassword(repository),
            loginWithGoogle = LoginWithGoogle(repository),
            sendPasswordReset = SendPasswordReset(repository),
            getCurrentUser = GetCurrentUser(repository),
        )
    }
}