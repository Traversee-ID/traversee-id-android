package com.alvindev.traverseeid.feature_settings.presentation.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_settings.domain.use_case.UseCasesSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val useCases: UseCasesSettings,
): ViewModel(){
    var state by mutableStateOf(LanguageState())
        private set

    fun saveLanguage(idLanguage: String) = viewModelScope.launch {
        useCases.changeLanguage(idLanguage).asFlow().collect { result ->
            state = when(result){
                is ResultState.Loading -> {
                    state.copy(
                        isSubmitting = true,
                        isSuccess = false,
                        error = null
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        isSubmitting = false,
                        isSuccess = true,
                        error = null
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        isSubmitting = false,
                        isSuccess = false,
                        error = result.error
                    )
                }
            }
        }
    }
}