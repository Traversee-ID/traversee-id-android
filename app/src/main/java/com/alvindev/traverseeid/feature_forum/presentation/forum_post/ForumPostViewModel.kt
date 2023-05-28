package com.alvindev.traverseeid.feature_forum.presentation.forum_post

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForumPostViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(ForumPostState())
        private set

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
}