package com.alvindev.traverseeid.feature_settings.presentation.edit_profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.moneysaver.core.common.ResultState
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.feature_settings.domain.use_case.UseCasesSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val useCases: UseCasesSettings,
    private val resourcesProvider: ResourcesProvider,
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

    fun onNameChange(name: String) {
        Log.d("EditProfileViewModel", "onNameChange: $name")
        state = state.copy(
            name = name,
        )
    }

    fun onSubmit() = viewModelScope.launch {
        state = state.copy(
            isSubmitting = true,
            isSuccess = false,
            error = "",
        )

        val name = state.name.trim()

        //check if name is empty
        if (name.isEmpty()) {
            state = state.copy(
                isSubmitting = false,
                isSuccess = false,
                error = resourcesProvider.getString(R.string.error_name_empty),
            )
            return@launch
        }

        useCases.updateProfile(name = name, photoUrl = state.selectedImagePicker?.toString()).asFlow().collect{
            state = when(it) {
                is ResultState.Loading -> {
                    state.copy(
                        isSubmitting = true,
                        isSuccess = false,
                    )
                }
                is ResultState.Success -> {
                    state.copy(
                        isSubmitting = false,
                        isSuccess = true,
                    )
                }
                is ResultState.Error -> {
                    state.copy(
                        isSubmitting = false,
                        isSuccess = false,
                        error = it.error ?: "",
                    )
                }
            }
        }
    }
}