package com.alvindev.traverseeid.feature_settings.domain.use_case

import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository

class Logout(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() {
        settingsRepository.logout()
    }
}