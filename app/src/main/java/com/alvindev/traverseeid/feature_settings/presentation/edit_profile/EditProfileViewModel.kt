package com.alvindev.traverseeid.feature_settings.presentation.edit_profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.alvindev.traverseeid.feature_settings.domain.use_case.UseCasesSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val useCases: UseCasesSettings,
) : ViewModel(){
    var state by mutableStateOf(EditProfileState())
        private set

    fun setUser(name: String?, email: String?, photoUrl: Uri?) {
        Log.d("EditProfileViewModel", "setUser: $name, $email, $photoUrl")

        state = state.copy(
            name = name ?: "",
            email = email ?: "",
            avatarUrl = photoUrl,
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

    fun onNameChange(name: String) {
        state = state.copy(
            name = name,
        )
    }
}