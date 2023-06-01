package com.alvindev.traverseeid.feature_forum.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCommentEntity
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostEntity
import kotlinx.coroutines.flow.Flow

interface ForumRepository {
    suspend fun createPost(title: String, text: String): LiveData<ResultState<ForumPostEntity>>

    fun getAllForumPosts(): Flow<PagingData<ForumPostEntity>>

    suspend fun likePost(postId: Int): LiveData<ResultState<ForumPostEntity>>

    suspend fun unlikePost(postId: Int): LiveData<ResultState<ForumPostEntity>>

    fun getForumComments(postId: Int): Flow<PagingData<ForumCommentEntity>>

    suspend fun createComment(postId: Int, text: String): LiveData<ResultState<ForumCommentEntity>>
}