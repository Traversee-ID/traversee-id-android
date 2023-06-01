package com.alvindev.traverseeid.feature_auth.domain.use_case

import androidx.lifecycle.LiveData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_auth.domain.repository.AuthRepository

class SendPasswordReset (
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String): LiveData<ResultState<Boolean>> {
        return authRepository.sendPasswordReset(email)
    }
}