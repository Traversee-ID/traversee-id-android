package com.alvindev.traverseeid.feature_auth.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_auth.domain.use_case.UseCasesAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: UseCasesAuth
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set
    init {
        checkUser()
    }

    fun checkUser() = viewModelScope.launch {
        useCases.getCurrentUser().asFlow().collect { result ->
            state = when (result) {
                is ResultState.Loading -> {
                    state.copy(
                        firebaseUser = null,
                        isLoading = true,
                        error = null,
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        firebaseUser = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        firebaseUser = null,
                        isLoading = false,
                        error = result.error,
                    )
                }
            }
        }
    }

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun onPasswordVisibilityChange() {
        state = state.copy(isPasswordVisible = !state.isPasswordVisible)
    }

    fun login() = viewModelScope.launch {
        //reset field error
        state = state.copy(error = null)
        val email = state.email.trim()
        val password = state.password.trim()

        if(email.isEmpty() || password.isEmpty()) {
            state = state.copy(error = "Email and password must not be empty")
            return@launch
        }

        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches().not()) {
            state = state.copy(error = "Email must be valid")
            return@launch
        }

        useCases.loginWithEmailPassword(email, password).asFlow().collect { result ->
            state = when (result) {
                is ResultState.Loading -> {
                    state.copy(
                        firebaseUser = null,
                        isSubmitting = true,
                        error = null,
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        firebaseUser = result.data,
                        isSubmitting = false,
                        error = null
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        firebaseUser = null,
                        isSubmitting = false,
                        error = result.error,
                    )
                }
            }
        }
    }
    fun loginWithGoogle(idToken: String) = viewModelScope.launch {
        useCases.loginWithGoogle(idToken).asFlow().collect { result ->
            state = when (result) {
                is ResultState.Loading -> {
                    state.copy(
                        firebaseUser = null,
                        isLoading = true,
                        error = null,
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        firebaseUser = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        firebaseUser = null,
                        isLoading = false,
                        error = result.error,
                    )
                }
            }
        }
    }

    fun setErrorMessage(message: String) {
        state = state.copy(error = message)
    }
}