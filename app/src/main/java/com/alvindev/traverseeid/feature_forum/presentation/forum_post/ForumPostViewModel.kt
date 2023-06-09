package com.alvindev.traverseeid.feature_forum.presentation.forum_post

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.R
import com.alvindev.traverseeid.core.util.ResourcesProvider
import com.alvindev.traverseeid.feature_forum.data.model.ForumPostBody
import com.alvindev.traverseeid.feature_forum.domain.use_case.UseCasesForum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ForumPostViewModel @Inject constructor(
    private val useCasesForum: UseCasesForum,
    private val resourcesProvider: ResourcesProvider,
) : ViewModel() {
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

    fun onTitleChange(title: String) {
        state = state.copy(
            title = title,
        )
    }

    fun onTextChange(text: String) {
        state = state.copy(
            text = text,
        )
    }

    fun onPostClick(campaignId: Int? = null, image: File? = null) = viewModelScope.launch {
        state = state.copy(
            isSubmitting = true,
            error = null,
        )

        val title = state.title.trim()
        val text = state.text.trim()

        if (title.isEmpty() || text.isEmpty()) {
            state = state.copy(
                isSubmitting = false,
                error = resourcesProvider.getString(R.string.title_text_empty),
            )
            return@launch
        }

        var imageMultipart: MultipartBody.Part? = null
        if (image != null) {
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imageMultipart = MultipartBody.Part.createFormData("image", image.name, requestImageFile)
        }

        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val textBody = text.toRequestBody("text/plain".toMediaTypeOrNull())
        val campaignIdBody = campaignId?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
        val body = ForumPostBody(
            title = titleBody,
            text = textBody,
            campaignId = campaignIdBody,
            image = imageMultipart,
        )

        useCasesForum.createPost(body).asFlow().collect {
            state = when (it) {
                ResultState.Loading -> state.copy(
                    isSubmitting = true,
                )
                is ResultState.Success -> state.copy(
                    isSubmitting = false,
                    isSuccess = true,
                )
                is ResultState.Error -> state.copy(
                    isSubmitting = false,
                    error = it.error,
                )
            }
        }
    }
}