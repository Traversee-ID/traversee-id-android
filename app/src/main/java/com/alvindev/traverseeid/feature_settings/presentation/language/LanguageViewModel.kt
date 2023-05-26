package com.alvindev.traverseeid.feature_settings.presentation.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.alvindev.traverseeid.feature_settings.domain.use_case.UseCasesSettings
import com.alvindev.traverseeid.feature_settings.presentation.edit_profile.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val useCases: UseCasesSettings,
): ViewModel(){
    var state by mutableStateOf(LanguageState())
        private set
}