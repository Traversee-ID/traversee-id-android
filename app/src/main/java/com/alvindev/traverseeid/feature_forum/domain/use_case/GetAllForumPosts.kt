package com.alvindev.traverseeid.feature_forum.domain.use_case

import com.alvindev.traverseeid.feature_forum.domain.repository.ForumRepository

class GetAllForumPosts(
    private val forumRepository: ForumRepository
) {
    operator fun invoke() = forumRepository.getAllForumPosts()
}