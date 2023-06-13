package com.alvindev.traverseeid.feature_settings.presentation.settings

import android.net.Uri
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
class SettingsViewModel @Inject constructor(
    private val useCases: UseCasesSettings,
) : ViewModel() {
    var state by mutableStateOf(SettingsState())
        private set

    fun logout() = viewModelScope.launch{
        useCases.logout().asFlow().collect {
            when(it){
                is ResultState.Success -> {
                    state = state.copy(
                        firebaseUser = null,
                        isLogout = true,
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        firebaseUser = null,
                        isLogout = true,
                    )
                }
                else -> {}
            }
        }
    }
}