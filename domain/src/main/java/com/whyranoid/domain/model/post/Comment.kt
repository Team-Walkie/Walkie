package com.whyranoid.domain.model.post

import com.whyranoid.domain.model.user.User

data class Comment(
    val postId: Long,
    val commenterId: Long,
    val date: String,
    val content: String,
    val commentId: Long,
    val commenter: User,
)
