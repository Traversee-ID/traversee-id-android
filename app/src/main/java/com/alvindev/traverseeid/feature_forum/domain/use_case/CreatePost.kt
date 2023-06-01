package com.alvindev.traverseeid.feature_forum.domain.use_case

import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository

class CreatePost(
    private val repository: ForumRepository
) {
    suspend operator fun invoke(
        title: String,
        text: String
    ) = repository.createPost(title, text)
}