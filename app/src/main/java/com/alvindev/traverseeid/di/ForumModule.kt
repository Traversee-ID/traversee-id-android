package com.alvindev.traverseeid.di

import com.alvindev.traverseeid.core.domain.repository.BaseAuthRepository
import com.alvindev.traverseeid.feature_forum.data.remote.ForumApi
import com.alvindev.traverseeid.feature_forum.data.repository.ForumRepositoryImpl
import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository
import com.alvindev.traverseeid.feature_forum.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ForumModule {
    @Provides
    @Singleton
    fun provideForumRepository(campaignApi: ForumApi): ForumRepository {
        return ForumRepositoryImpl(campaignApi)
    }

    @Provides
    @Singleton
    fun provideForumApi(): ForumApi {
        return ApiConfig.getApiService(ForumApi::class.java)
    }

    @Provides
    @Singleton
    fun provideForumUseCases(repository: ForumRepository): UseCasesForum {
        return UseCasesForum(
            createPost = CreatePost(repository),
            getAllForumPosts = GetAllForumPosts(repository),
            likePost = LikePost(repository),
            unlikePost = UnlikePost(repository),
            getForumComments = GetForumComments(repository),
            createComment = CreateComment(repository),
            deleteComment = DeleteComment(repository),
        )
    }
}