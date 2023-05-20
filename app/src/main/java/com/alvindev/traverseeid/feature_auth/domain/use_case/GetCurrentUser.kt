package com.alvindev.traverseeid.feature_auth.domain.use_case

import androidx.lifecycle.LiveData
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.feature_auth.domain.entity.User
import com.alvindev.traverseeid.feature_auth.domain.repository.AuthRepository

class GetCurrentUser(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): LiveData<ResultState<User?>> {
        return authRepository.getCurrentUser()
    }
}