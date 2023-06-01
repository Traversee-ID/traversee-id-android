package com.alvindev.traverseeid.feature_forum.data.remote

import com.alvindev.traverseeid.feature_forum.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ForumApi {
    @POST("forums")
    suspend fun createPost(@Body body: ForumPostBody) : ForumPostResponse

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
    ): ForumCreateCommentResponse

    @DELETE("forums/{id}/comments/{commentId}")
    suspend fun deleteComment(
        @Path("id") postId: Int,
        @Path("commentId") commentId: Int
    ): ForumDeleteCommentResponse
}