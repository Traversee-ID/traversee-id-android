package com.alvindev.traverseeid.feature_forum.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alvindev.traverseeid.core.common.ResultState
import com.alvindev.traverseeid.feature_forum.data.model.ForumCommentBody
import com.alvindev.traverseeid.feature_forum.data.model.ForumPostBody
import com.alvindev.traverseeid.feature_forum.data.paging_source.ForumPagingSource
import com.alvindev.traverseeid.feature_forum.data.remote.ForumApi
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumCommentEntity
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostEntity
import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val forumApi: ForumApi
) : ForumRepository {

    override suspend fun createPost(
        title: String,
        text: String
    ): LiveData<ResultState<ForumPostEntity>> =
        liveData {
            try {
                val body = ForumPostBody(title, text)
                val response = forumApi.createPost(body)
                response.data?.let {
                    emit(ResultState.Success(it))
                } ?: emit(ResultState.Error(response.message.toString()))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override fun getAllForumPosts(): Flow<PagingData<ForumPostEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                ForumPagingSource(forumApi)
            }
        ).flow
    }

    override suspend fun likePost(postId: Int): LiveData<ResultState<ForumPostEntity>> =
        liveData {
            try {
                val response = forumApi.likePost(postId)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "Error: ${response.code()} ${response.message()}"
                    }
                    emit(ResultState.Error(errorMessage))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: e.message.toString()))
            }
        }

    override suspend fun unlikePost(postId: Int): LiveData<ResultState<ForumPostEntity>> =
        liveData {
            try {
                val response = forumApi.unlikePost(postId)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "Error: ${response.code()} ${response.message()}"
                    }
                    emit(ResultState.Error(errorMessage))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: e.message.toString()))
            }
        }

    override  fun getForumComments(postId: Int, page: Int): Flow<ResultState<List<ForumCommentEntity>>> = flow{
        try {
            val response = forumApi.getForumComments(postId, page)
            response.data?.let {
                emit(ResultState.Success(it))
            } ?: emit(ResultState.Error("Unexpected Error!"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message.toString()))
        }
    }

    override suspend fun createComment(
        postId: Int,
        text: String
    ): LiveData<ResultState<ForumCommentEntity>> =
        liveData {
            try {
                val body = ForumCommentBody(text)
                val response = forumApi.createComment(postId, body)
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "Error: ${response.code()} ${response.message()}"
                    }
                    emit(ResultState.Error(errorMessage))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }

    override suspend fun deleteComment(postId: Int, commentId: Int): LiveData<ResultState<String>> =
        liveData {
            try {
                val response = forumApi.deleteComment(postId, commentId)
                if (response.isSuccessful) {
                    val responseData = response.body()?.message
                    responseData?.let {
                        emit(ResultState.Success(it))
                    } ?: emit(ResultState.Error("Unexpected Error!"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        val errorJson = JSONObject(errorBody)
                        errorJson.getString("message")
                    } catch (e: JSONException) {
                        "Error: ${response.code()} ${response.message()}"
                    }
                    emit(ResultState.Error(errorMessage))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e.message.toString()))
            }
        }
}