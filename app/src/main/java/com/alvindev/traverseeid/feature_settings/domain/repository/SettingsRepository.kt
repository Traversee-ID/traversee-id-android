package com.alvindev.traverseeid.feature_settings.domain.repository

import androidx.lifecycle.LiveData
import com.alvindev.traverseeid.core.common.ResultState

interface SettingsRepository {
    fun logout()
    suspend fun changeLanguage(idLanguage: String): LiveData<ResultState<String>>

    suspend fun updateProfile(name: String, photoUrl: String?): LiveData<ResultState<String>>
}
