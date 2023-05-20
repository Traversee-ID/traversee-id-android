package com.alvindev.traverseeid.feature_auth.domain.use_case

import androidx.lifecycle.LiveData
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.feature_auth.domain.entity.User
import com.alvindev.traverseeid.feature_auth.domain.repository.AuthRepository

class LoginWithGoogle(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(idToken: String): LiveData<ResultState<User?>> {
        return authRepository.loginWithGoogle(idToken)
    }
}