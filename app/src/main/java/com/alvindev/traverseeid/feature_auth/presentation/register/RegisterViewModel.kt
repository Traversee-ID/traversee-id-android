package com.alvindev.traverseeid.feature_auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.feature_auth.domain.use_case.UseCasesAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCases: UseCasesAuth
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set
    init {
        checkUser()
    }

    private fun checkUser() = viewModelScope.launch {
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

    fun onPasswordConfirmationChange(passwordConfirmation: String) {
        state = state.copy(passswordConfirmation = passwordConfirmation)
    }

    fun onPasswordConfirmationVisibilityChange() {
        state = state.copy(isPasswordConfirmationVisible = !state.isPasswordConfirmationVisible)
    }

    fun register() = viewModelScope.launch {
        //reset field error
        state = state.copy(error = null)

        if(state.email.isEmpty() || state.password.isEmpty()) {
            state = state.copy(error = "Email and password must not be empty")
            return@launch
        }

        if(android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches().not()) {
            state = state.copy(error = "Email must be valid")
            return@launch
        }

        if(state.password.length < 6) {
            state = state.copy(error = "Password must be at least 6 characters")
            return@launch
        }

        useCases.registerWithEmailPassword(state.email, state.password).asFlow().collect { result ->
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