package com.alvindev.traverseeid.core.data.local

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alvindev.traverseeid.core.domain.entity.UserPreference
import com.alvindev.traverseeid.core.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject


class UserDataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserDataStoreRepository {
    override suspend fun getUser(): UserPreference {
        val user = dataStore.data.map {
            UserPreference(
                name = it[NAME_KEY] ?: "",
                email = it[EMAIL_KEY] ?: "",
                avatarUrl = Uri.parse(it[AVATAR_KEY] ?: ""),
                token = it[TOKEN_KEY] ?: "",
                uid = it[USERID_KEY] ?: "",
                language = it[LANGUAGE_KEY] ?: Locale.getDefault().language
            )
        }.first()
        return user
    }

    override suspend fun saveUserLogin(user: UserPreference) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name ?: ""
            preferences[EMAIL_KEY] = user.email ?: ""
            preferences[AVATAR_KEY] = user.avatarUrl.toString()
            preferences[TOKEN_KEY] = user.token ?: ""
            preferences[USERID_KEY] = user.uid ?: ""
        }
    }

    override suspend fun clearUserLogin() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[AVATAR_KEY] = ""
            preferences[TOKEN_KEY] = ""
            preferences[USERID_KEY] = ""
        }
    }

    override suspend fun setDefaultLanguage(langId: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = langId
        }
    }

    override suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val AVATAR_KEY = stringPreferencesKey("avatarUrl")
        private val USERID_KEY = stringPreferencesKey("uid")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
    }
}