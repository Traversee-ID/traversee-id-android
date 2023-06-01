package com.alvindev.traverseeid.feature_forum.domain.use_case

import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository

class DeleteComment(
    private val repository: ForumRepository
) {
    suspend operator fun invoke(postId: Int, commentId: Int) = repository.deleteComment(postId, commentId)
}