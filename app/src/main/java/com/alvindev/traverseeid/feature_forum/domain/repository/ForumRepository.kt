package com.alvindev.traverseeid.feature_forum.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_forum.data.model.ForumPostBody
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCommentEntity
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem
import kotlinx.coroutines.flow.Flow

interface ForumRepository {
    suspend fun createPost(body: ForumPostBody): LiveData<ResultState<ForumPostItem>>

    fun getAllForumPosts(): Flow<PagingData<ForumPostItem>>

    suspend fun likePost(postId: Int): LiveData<ResultState<ForumPostItem>>

    suspend fun unlikePost(postId: Int): LiveData<ResultState<ForumPostItem>>

    fun getForumComments(postId: Int, page:Int): Flow<ResultState<List<ForumCommentEntity>>>

    suspend fun createComment(postId: Int, text: String): LiveData<ResultState<ForumCommentEntity>>
    suspend fun deleteComment(postId: Int, commentId: Int): LiveData<ResultState<String>>
}