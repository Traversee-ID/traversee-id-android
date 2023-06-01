package com.alvindev.traverseeid.feature_forum.domain.use_case

data class UseCasesForum(
    val createPost: CreatePost,
    val getAllForumPosts: GetAllForumPosts,
    val likePost: LikePost,
    val unlikePost: UnlikePost,
    val getForumComments: GetForumComments,
    val createComment: CreateComment,
    val deleteComment: DeleteComment,
)
