package com.alvindev.traverseeid.feature_settings.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.core.data.local.UserDataStoreRepositoryImpl
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val authenticator: BaseAuthRepository,
    private val dataStore: UserDataStoreRepositoryImpl
) : SettingsRepository {
    override fun logout() {
        authenticator.logout()
    }

    override suspend fun changeLanguage(idLanguage: String): LiveData<ResultState<String>> = liveData{
        try {
            emit(ResultState.Loading)
            dataStore.setDefaultLanguage(idLanguage)
            emit(ResultState.Success(idLanguage))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }
}