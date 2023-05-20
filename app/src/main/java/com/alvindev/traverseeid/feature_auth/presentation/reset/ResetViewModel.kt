package com.alvindev.traverseeid.feature_auth.presentation.reset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.feature_auth.domain.use_case.UseCasesAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetViewModel @Inject constructor(
    private val useCases: UseCasesAuth
) : ViewModel(){
    var state by mutableStateOf(ResetState())
        private set

    fun onEmailChange(email: String){
        state = state.copy(email = email)
    }

    fun reset() = viewModelScope.launch {
        //reset field error
        state = state.copy(error = null)

        if(state.email.isEmpty()) {
            state = state.copy(error = "Email must not be empty")
            return@launch
        }

        if(android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches().not()) {
            state = state.copy(error = "Email must be valid")
            return@launch
        }

        useCases.sendPasswordReset(state.email).asFlow().collect { result ->
            state = when(result) {
                is com.alvindev.moneysaver.core.common.ResultState.Loading -> {
                    state.copy(
                        isLoading = true,
                        error = null,
                        isSuccess = false,
                    )
                }
                is com.alvindev.moneysaver.core.common.ResultState.Success -> {
                    state.copy(
                        isLoading = false,
                        error = null,
                        isSuccess = true,
                    )
                }
                is com.alvindev.moneysaver.core.common.ResultState.Error -> {
                    state.copy(
                        isLoading = false,
                        error = result.error,
                        isSuccess = false,
                    )
                }
            }
        }
    }
}