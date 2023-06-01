package com.alvindev.traverseeid.feature_forum.domain.use_case

import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository

class UnlikePost(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(postId: Int) = forumRepository.unlikePost(postId)
}