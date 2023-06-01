package com.alvindev.traverseeid.feature_auth.domain.use_case

import androidx.lifecycle.LiveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_auth.domain.entity.User
import com.alvindev.traverseeid.feature_auth.domain.repository.AuthRepository

class RegisterWithEmailPassword(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(name: String, email: String, password: String): LiveData<ResultState<User?>> {
        return authRepository.registerWithEmailPassword(name, email, password)
    }
}