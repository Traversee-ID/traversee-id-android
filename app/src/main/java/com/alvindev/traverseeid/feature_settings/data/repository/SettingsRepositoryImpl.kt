package com.alvindev.traverseeid.feature_settings.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.core.data.local.UserDataStoreRepositoryImpl
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_settings.data.remote.SettingsApi
import com.alvindev.traverseeid.feature_settings.domain.repository.SettingsRepository
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val authenticator: BaseAuthRepository,
    private val dataStore: UserDataStoreRepositoryImpl,
    private val settingsApi: SettingsApi
) : SettingsRepository {
    override suspend fun logout(): LiveData<ResultState<Boolean>> = liveData {
        try {
            emit(ResultState.Loading)
            dataStore.clearUserLogin()
            authenticator.logout()
            emit(ResultState.Success(true))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun changeLanguage(idLanguage: String): LiveData<ResultState<String>> = liveData{
        try {
            emit(ResultState.Loading)
            dataStore.setDefaultLanguage(idLanguage)
            emit(ResultState.Success(idLanguage))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun updateProfile(
        name: String
    ): LiveData<ResultState<String>> {
        return liveData {
            try {
                emit(ResultState.Loading)
                authenticator.updateProfile(name)
                emit(ResultState.Success("Success"))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }
    }

    override suspend fun updateProfilePicture(file: MultipartBody.Part): LiveData<ResultState<String>>{
        return liveData {
            try {
                emit(ResultState.Loading)
                val response = settingsApi.updateProfilePicture(file)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "Error: ${response.code()} ${response.message()}"
                    }
                    emit(ResultState.Error(errorMessage))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }
    }
}