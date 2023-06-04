package com.alvindev.traverseeid.feature_auth.data.repository

import ApiConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.TraverseeApplication
import com.alvindev.traverseeid.core.data.local.UserDataStoreRepositoryImpl
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_auth.domain.entity.User
import com.alvindev.traverseeid.feature_auth.domain.mapper.firebaseUserToUser
import com.alvindev.traverseeid.feature_auth.domain.mapper.firebaseUserToUserPreference
import com.alvindev.traverseeid.feature_auth.domain.repository.AuthRepository
import java.util.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authenticator: BaseAuthRepository,
    private val dataStore: UserDataStoreRepositoryImpl
) : AuthRepository {

    override suspend fun loginWithEmailPassword(
        email: String,
        password: String
    ): LiveData<ResultState<User?>> = liveData {
        try {
            emit(ResultState.Loading)
            val firebaseUser = authenticator.loginWithEmailPassword(email, password)
            val user = firebaseUser?.let { firebaseUserToUser(it) }
            ApiConfig.TOKEN = authenticator.getUserToken() ?: ""
            emit(ResultState.Success(user))
        } catch (e: Exception) {
            Log.e(TAG, "login: ${e.message.toString()}")
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun loginWithGoogle(idToken: String): LiveData<ResultState<User?>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val firebaseUser = authenticator.loginWithGoogle(idToken)
                val user = firebaseUser?.let { firebaseUserToUser(it) }
                ApiConfig.TOKEN = authenticator.getUserToken() ?: ""
                emit(ResultState.Success(user))
            } catch (e: Exception) {
                Log.e(TAG, "login: ${e.message.toString()}")
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun registerWithEmailPassword(
        name: String,
        email: String,
        password: String
    ): LiveData<ResultState<User?>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val firebaseUser = authenticator.registerWithEmailPassword(email, password)
                firebaseUser?.let {
                    authenticator.updateProfile(name, null)
                }
                val user = firebaseUser?.let { firebaseUserToUser(it) }
                ApiConfig.TOKEN = authenticator.getUserToken() ?: ""
                emit(ResultState.Success(user))
            } catch (e: Exception) {
                Log.e(TAG, "register: ${e.message.toString()}")
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override fun getCurrentUser(): LiveData<ResultState<User?>> = liveData {
        try {
            emit(ResultState.Loading)

            //get user data
            val firebaseUser = authenticator.getCurrentUser()
            val user = firebaseUser?.let { firebaseUserToUser(it) }
            var userPreference = firebaseUser?.let { firebaseUserToUserPreference(it) }

            //get token
            val token = authenticator.getUserToken()
            userPreference = userPreference?.copy(token = token)
            userPreference?.let { dataStore.saveUserLogin(it) }
            token?.let { ApiConfig.TOKEN = it }
            Log.d(TAG, "getCurrentUser: $token")

            //get user language preference
            val userDataStore = dataStore.getUser()
            userDataStore.let {
                TraverseeApplication.LANGUAGE = it.language ?: Locale.getDefault().language
            }

            emit(ResultState.Success(user))
        } catch (e: Exception) {
            Log.e(TAG, "getCurrentUser: ${e.message.toString()}")
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun sendPasswordReset(email: String): LiveData<ResultState<Boolean>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val result = authenticator.sendPasswordReset(email)
                emit(ResultState.Success(result))
            } catch (e: Exception) {
                Log.e(TAG, "sendPasswordReset: ${e.message.toString()}")
                emit(ResultState.Error(e.message.toString()))
            }
        }

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}