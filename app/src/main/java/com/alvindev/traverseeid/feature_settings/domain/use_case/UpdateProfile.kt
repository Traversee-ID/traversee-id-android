package com.alvindev.traverseeid.feature_settings.domain.use_case

import androidx.lifecycle.LiveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository

class UpdateProfile(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(name: String): LiveData<ResultState<String>> {
        return settingsRepository.updateProfile(name)
    }
}