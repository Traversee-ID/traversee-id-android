package com.alvindev.traverseeid.feature_forum.domain.use_case

import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository

class DeletePost(
    private val repository: ForumRepository
) {
    suspend operator fun invoke(postId: Int) = repository.deletePost(postId)
}