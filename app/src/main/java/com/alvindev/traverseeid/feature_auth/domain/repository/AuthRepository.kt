package com.alvindev.traverseeid.feature_auth.domain.repository

import androidx.lifecycle.LiveData
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.feature_auth.domain.entity.User

interface AuthRepository {
    suspend fun loginWithEmailPassword(email: String, password: String): LiveData<ResultState<User?>>

    suspend fun loginWithGoogle(idToken: String): LiveData<ResultState<User?>>

    suspend fun registerWithEmailPassword(name: String, email: String, password: String): LiveData<ResultState<User?>>

    fun getCurrentUser(): LiveData<ResultState<User?>>

    suspend fun sendPasswordReset(email: String) : LiveData<ResultState<Boolean>>
}