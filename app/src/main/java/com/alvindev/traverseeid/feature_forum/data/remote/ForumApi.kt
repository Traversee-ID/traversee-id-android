package com.alvindev.traverseeid.feature_forum.data.remote

import com.alvindev.traverseeid.feature_forum.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ForumApi {
    @POST("forums")
    @Multipart
    suspend fun createPost(
        @Part file: MultipartBody.Part? = null,
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
    ): Response<ForumPostResponse>

    @GET("forums")
    suspend fun getAllForumPosts(
        @Query("page") page: Int,
    ): ForumResponse

    @POST("forums/{id}/likes")
    suspend fun likePost(
        @Path("id") postId: Int
    ): Response<ForumPostResponse>

    @DELETE("forums/{id}/likes")
    suspend fun unlikePost(
        @Path("id") postId: Int
    ): Response<ForumPostResponse>

    @GET("forums/{id}/comments")
    suspend fun getForumComments(
        @Path("id") postId: Int,
        @Query("page") page: Int,
    ): ForumCommentResponse

    @POST("forums/{id}/comments")
    suspend fun createComment(
        @Path("id") postId: Int,
        @Body body: ForumCommentBody
    ): Response<ForumCreateCommentResponse>

    @DELETE("forums/{id}/comments/{commentId}")
    suspend fun deleteComment(
        @Path("id") postId: Int,
        @Path("commentId") commentId: Int
    ): Response<ForumDeleteCommentResponse>

    @GET("users/{uid}/forums")
    suspend fun getUserForumPosts(
        @Path("uid") uid: String,
        @Query("page") page: Int,
    ): ForumResponse

    @DELETE("forums/{id}")
    suspend fun deletePost(
        @Path("id") postId: Int
    ): Response<ForumDeletePostResponse>
}