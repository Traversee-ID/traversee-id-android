package com.alvindev.traverseeid.core.domain.repository

import com.alvindev.traverseeid.core.domain.entity.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun getUser(): UserPreference

    suspend fun saveUserLogin(user: UserPreference)

    suspend fun clearUserLogin()

    suspend fun setDefaultLanguage(langId: String)

    suspend fun saveUserToken(token: String)
}