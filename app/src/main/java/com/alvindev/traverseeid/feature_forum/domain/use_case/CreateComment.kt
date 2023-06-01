package com.alvindev.traverseeid.feature_forum.domain.use_case

import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository

class CreateComment(
    private val repository: ForumRepository
) {
    suspend operator fun invoke(
        forumId: Int,
        text: String
    ) = repository.createComment(forumId, text)
}