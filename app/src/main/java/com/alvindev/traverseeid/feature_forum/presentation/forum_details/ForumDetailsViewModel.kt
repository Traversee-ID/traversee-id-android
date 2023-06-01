package com.alvindev.traverseeid.feature_forum.presentation.forum_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_forum.domain.use_case.UseCasesForum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumDetailsViewModel @Inject constructor(
    private val useCases: UseCasesForum
) : ViewModel() {
    var state by mutableStateOf(ForumDetailsState())
        private set

    override fun onCleared() {
        state = state.copy(
            page = 1,
            listState = ListState.IDLE,
            canPaginate = false
        )
        super.onCleared()
    }

    fun getForumComments(postId: Int) = viewModelScope.launch {
        if (state.page == 1 || (state.page != 1 && state.canPaginate) && state.listState == ListState.IDLE) {
            state = state.copy(
                listState = if (state.page == 1) ListState.LOADING else ListState.PAGINATING
            )

            useCases.getForumComments(postId, state.page).collect {
                when (it) {
                    is ResultState.Error -> {
                        state = state.copy(
                            error = it.error,
                            listState = ListState.IDLE,
                        )
                    }
                    is ResultState.Success -> {
                        state = state.copy(
                            comments = if (state.page == 1) it.data else state.comments + it.data,
                            page = state.page + 1,
                            canPaginate = it.data.size < 5,
                            listState = ListState.IDLE,
                        )
                    }
                    else -> {
                        state = state.copy(
                            error = null,
                            listState = if (state.page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST,
                        )
                    }
                }
            }
        }
    }

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
                        comments = state.comments + it.data,
                        isSubmitting = false,
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        error = it.error,
                        isSubmitting = false,
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

    fun setShowDialog(isShow: Boolean) {
        state = state.copy(
            isShowDialog = isShow
        )
    }

    fun setCommentId(commentId: Int) {
        state = state.copy(
            commentId = commentId
        )
    }

    fun deleteComment(postId: Int, commentId: Int) = viewModelScope.launch {
        useCases.deleteComment(postId, commentId).asFlow().collect {
            when (it) {
                is ResultState.Success -> {
                    state = state.copy(
                        isSuccess = true,
                        isShowDialog = false,
                        comments = state.comments.filter { comment -> comment.id != commentId },
                        isSubmitting = false,
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        error = it.error,
                        isSubmitting = false,
                        isShowDialog = false,
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
}