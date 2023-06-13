package com.alvindev.traverseeid.feature_settings.domain.repository

import androidx.lifecycle.LiveData
import com.alvindev.traverseeid.core.common.ResultState
import okhttp3.MultipartBody

interface SettingsRepository {
    suspend fun logout(): LiveData<ResultState<Boolean>>
    suspend fun changeLanguage(idLanguage: String): LiveData<ResultState<String>>
    suspend fun updateProfile(name: String): LiveData<ResultState<String>>
    suspend fun updateProfilePicture(file: MultipartBody.Part): LiveData<ResultState<String>>
}
