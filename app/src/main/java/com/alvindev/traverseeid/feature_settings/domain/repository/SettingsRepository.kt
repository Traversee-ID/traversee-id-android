package com.alvindev.traverseeid.feature_settings.domain.repository

import androidx.lifecycle.LiveData
import com.alvindev.moneysaver.core.common.ResultState

interface SettingsRepository {
    fun logout()

    suspend fun changeLanguage(idLanguage: String): LiveData<ResultState<String>>
}
