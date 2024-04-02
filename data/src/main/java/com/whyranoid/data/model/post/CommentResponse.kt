package com.whyranoid.data.model.post

import com.whyranoid.domain.model.user.User

data class CommentResponse(
    val postId: Long,
    val commenterId: Long,
    val date: String,
    val content: String,
    val commentId: Long,
    val commenter: User,
)
