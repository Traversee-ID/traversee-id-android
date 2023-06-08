package com.alvindev.traverseeid.feature_forum.presentation.forum_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_forum.domain.use_case.UseCasesForum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumUserViewModel @Inject constructor(
    private val useCases: UseCasesForum
):ViewModel() {
    var state by mutableStateOf(ForumUserState())
        private set

    fun getAllUserForumPosts() = useCases.getUserForumPosts().cachedIn(viewModelScope)

    fun likePost(postId: Int) = viewModelScope.launch {
        useCases.likePost(postId).asFlow().collect{
            state = when(it) {
                is ResultState.Error -> {
                    state.copy(error =it.error)
                }
                is ResultState.Success -> {
                    state.copy(post = it.data)
                }
                else -> {
                    state.copy(
                        error = null,
                        post = null
                    )
                }
            }
        }
    }

    fun unlikePost(postId: Int) = viewModelScope.launch {
        useCases.unlikePost(postId).asFlow().collect{
            state = when(it) {
                is ResultState.Error -> {
                    state.copy(error =it.error)
                }
                is ResultState.Success -> {
                    state.copy(post = it.data)
                }
                else -> {
                    state.copy(
                        error = null,
                        post = null
                    )
                }
            }
        }
    }
}