package com.alvindev.traverseeid.feature_forum.domain.use_case

import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository

class GetUserForumPosts(
    private val repository: ForumRepository
) {
    operator fun invoke() = repository.getUserForumPosts()
}