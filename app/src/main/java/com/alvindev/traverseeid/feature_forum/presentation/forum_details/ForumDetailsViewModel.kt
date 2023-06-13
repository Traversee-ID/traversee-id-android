package com.alvindev.traverseeid.feature_forum.presentation.forum_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_forum.domain.constant.DialogType
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
                            listState = if (state.page == 1) ListState.ERROR else ListState.PAGINATION_EXHAUST,
                        )
                    }
                    is ResultState.Success -> {
                        state = state.copy(
                            comments = if (state.page == 1) it.data else state.comments + it.data,
                            page = state.page + 1,
                            canPaginate = it.data.size >= 5,
                            listState = ListState.IDLE,
                        )
                    }
                    else -> {
                        state = state.copy(
                            error = null,
                            listState = ListState.LOADING,
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

    fun setIsSuccess(isSuccess: Boolean) {
        state = state.copy(
            isSuccess = isSuccess,
        )
    }

    fun setShowDialog(dialog: DialogType?) {
        state = state.copy(
            isShowDialog = dialog
        )
    }

    fun setCommentId(commentId: Int) {
        state = state.copy(
            commentId = commentId
        )
    }

    fun setTotalLikes(totalLikes: Int) {
        state = state.copy(
            totalLikes = totalLikes,
        )
    }

    fun setTotalComments(totalComments: Int) {
        state = state.copy(
            totalComments = totalComments,
        )
    }

    fun setIsLiked(isLiked: Boolean) {
        state = state.copy(
            isLiked = isLiked,
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
                    val comment = it.data
                    val comments = state.comments.toMutableList()
                    comments.add(0, comment)
                    state = state.copy(
                        text = "",
                        isSuccess = true,
                        comments = comments,
                        isSubmitting = false,
                        totalComments = state.totalComments + 1,
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


    fun deleteComment(postId: Int, commentId: Int) = viewModelScope.launch {
        useCases.deleteComment(postId, commentId).asFlow().collect {
            when (it) {
                is ResultState.Success -> {
                    state = state.copy(
                        isSuccess = true,
                        isShowDialog = null,
                        comments = state.comments.filter { comment -> comment.id != commentId },
                        isSubmitting = false,
                        totalComments = state.totalComments - 1,
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        error = it.error,
                        isSubmitting = false,
                        isShowDialog = null,
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

    fun likePost(postId: Int) = viewModelScope.launch {
        useCases.likePost(postId).asFlow().collect {
            state = when (it) {
                is ResultState.Error -> {
                    state.copy(error = it.error)
                }
                is ResultState.Success -> {
                    state.copy(
                        error = null,
                        isLiked = true,
                        totalLikes = state.totalLikes + 1,
                    )
                }
                else -> {
                    state.copy(
                        error = null,
                    )
                }
            }
        }
    }

    fun unlikePost(postId: Int) = viewModelScope.launch {
        useCases.unlikePost(postId).asFlow().collect {
            state = when (it) {
                is ResultState.Error -> {
                    state.copy(error = it.error)
                }
                is ResultState.Success -> {
                    state.copy(
                        error = null,
                        isLiked = false,
                        totalLikes = state.totalLikes - 1,
                    )
                }
                else -> {
                    state.copy(
                        error = null,
                    )
                }
            }
        }
    }

    fun deletePost(postId: Int) = viewModelScope.launch {
        useCases.deletePost(postId).asFlow().collect {
            when (it) {
                is ResultState.Success -> {
                    state = state.copy(
                        isDeleted = true,
                        isShowDialog = null,
                    )
                }
                is ResultState.Error -> {
                    state = state.copy(
                        error = it.error,
                        isSubmitting = false,
                        isShowDialog = null,
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