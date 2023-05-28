package com.alvindev.traverseeid.feature_settings.domain.use_case

import androidx.lifecycle.LiveData
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository

class ChangeLanguage(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(idLanguage: String): LiveData<ResultState<String>> {
        return settingsRepository.changeLanguage(idLanguage)
    }
}