package com.alvindev.traverseeid.feature_forum.presentation.forum_details

import com.alvindev.traverseeid.core.common.ListState
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCommentEntity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class ForumDetailsState(
    val error: String? = null,
    val isSuccess: Boolean = false,
    val isSubmitting: Boolean = false,
    val text: String = "",
    val userId: String = Firebase.auth.currentUser?.uid ?: "",
    val isShowDialog: Boolean = false,
    val commentId: Int = 0,
    val comment: ForumCommentEntity? = null,
    val comments: List<ForumCommentEntity> = emptyList(),
    val page : Int = 1,
    val canPaginate : Boolean = true,
    val listState: ListState = ListState.IDLE,
)
