package com.alvindev.traverseeid.feature_settings.domain.use_case

import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository
import okhttp3.MultipartBody

class UpdateProfilePicture(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(file: MultipartBody.Part) = repository.updateProfilePicture(file)
}