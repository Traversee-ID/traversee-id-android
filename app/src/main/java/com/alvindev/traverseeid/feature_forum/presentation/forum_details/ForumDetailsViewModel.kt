package com.alvindev.traverseeid.feature_forum.presentation.forum_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_forum.domain.use_case.UseCasesForum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumDetailsViewModel @Inject constructor(
    private val useCases: UseCasesForum
): ViewModel() {
    var state by mutableStateOf(ForumDetailsState())
        private set

    fun getForumComments(postId: Int) = useCases.getForumComments(postId).cachedIn(viewModelScope)

    fun onCommentChange(comment: String) {
        state = state.copy(
            text = comment,
        )
    }

    fun createComment(forumId: Int) = viewModelScope.launch {
        state = state.copy(
            isSubmitting = true,
            error = null,
            isSuccess = false,
        )

        useCases.createComment(forumId, state.text).asFlow().collect {
            when (it) {
                is ResultState.Success -> {
                    state = state.copy(
                        text = "",
                        isSuccess = true,
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        error = it.error,
                    )
                }
                is ResultState.Loading -> {
                    state = state.copy(
                        isSubmitting = true,
                    )
                }
            }
        }
    }

    fun setIsSuccess(isSuccess: Boolean) {
        state = state.copy(
            isSuccess = isSuccess,
        )
    }
}