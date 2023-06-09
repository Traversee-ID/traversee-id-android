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
import com.alvindev.traverseeid.feature_forum.domain.entity.ForumPostItem
import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val forumApi: ForumApi
) : ForumRepository {

    override suspend fun createPost(
        body: ForumPostBody
    ): LiveData<ResultState<ForumPostItem>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val map = mutableMapOf<String, RequestBody>()
                map["title"] = body.title
                map["text"] = body.text
                if(body.campaignId != null) {
                    map["campaign_id"] = body.campaignId
                }

                val response = forumApi.createPost(
                    file = body.image,
                    map = map
                )
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

    override fun getAllForumPosts(): Flow<PagingData<ForumPostItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                ForumPagingSource(forumApi)
            }
        ).flow
    }

    override suspend fun likePost(postId: Int): LiveData<ResultState<ForumPostItem>> =
        liveData {
            try {
                emit(ResultState.Loading)
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

    override suspend fun unlikePost(postId: Int): LiveData<ResultState<ForumPostItem>> =
        liveData {
            try {
                emit(ResultState.Loading)
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

    override suspend fun getForumComments(postId: Int, page: Int): Flow<ResultState<List<ForumCommentEntity>>> = flow{
        try {
            emit(ResultState.Loading)
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
                emit(ResultState.Loading)
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
                emit(ResultState.Loading)
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

    override fun getUserForumPosts(): Flow<PagingData<ForumPostItem>>{
        val userId = Firebase.auth.currentUser?.uid ?: ""
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                ForumPagingSource(forumApi, userId)
            }
        ).flow
    }

    override suspend fun deletePost(postId: Int): LiveData<ResultState<String>> =
        liveData {
            try {
                emit(ResultState.Loading)
                val response = forumApi.deletePost(postId)
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