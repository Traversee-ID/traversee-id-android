package com.alvindev.traverseeid.feature_settings.presentation.settings

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.alvindev.traverseeid.feature_settings.domain.use_case.UseCasesSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: UseCasesSettings,
) : ViewModel() {
    var state by mutableStateOf(SettingsState())
        private set

    fun logout() {
        useCases.logout()
        state.copy(
            firebaseUser = null,
            isLogout = true,
        )
    }
    fun setShowDialog(isShow: Boolean) {
        state = state.copy(
            isShowDialog = isShow,
        )
    }
    fun onSelectedImagePicker(uri: Uri?) {
        state = state.copy(
            selectedImagePicker = uri,
        )
    }
    fun onLookPicture(uri: Uri) {

    }
}