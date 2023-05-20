package com.alvindev.traverseeid.feature_auth.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_auth.domain.entity.User
import com.alvindev.traverseeid.feature_auth.domain.mapper.firebaseUserToUser
import com.alvindev.traverseeid.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authenticator: BaseAuthRepository
) : AuthRepository {

    override suspend fun loginWithEmailPassword(
        email: String,
        password: String
    ): LiveData<ResultState<User?>> = liveData {
        try {
            emit(ResultState.Loading)
            val firebaseUser = authenticator.loginWithEmailPassword(email, password)
            val user = firebaseUser?.let { firebaseUserToUser(it) }
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
                emit(ResultState.Success(user))
            } catch (e: Exception) {
                Log.e(TAG, "login: ${e.message.toString()}")
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun registerWithEmailPassword(email: String, password: String): LiveData<ResultState<User?>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val firebaseUser = authenticator.registerWithEmailPassword(email, password)
                val user = firebaseUser?.let { firebaseUserToUser(it) }
                emit(ResultState.Success(user))
            } catch (e: Exception) {
                Log.e(TAG, "register: ${e.message.toString()}")
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override fun getCurrentUser(): LiveData<ResultState<User?>> = liveData {
        try {
            emit(ResultState.Loading)
            val firebaseUser = authenticator.getCurrentUser()
            val user = firebaseUser?.let { firebaseUserToUser(it) }
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