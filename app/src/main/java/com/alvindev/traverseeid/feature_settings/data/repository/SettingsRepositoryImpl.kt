package com.alvindev.traverseeid.feature_settings.data.repository

import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val authenticator: BaseAuthRepository
) : SettingsRepository {
    override fun logout() {
        authenticator.logout()
    }
}