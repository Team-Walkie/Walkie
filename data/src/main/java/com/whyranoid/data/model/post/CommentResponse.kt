package com.whyranoid.data.model.post

import com.whyranoid.data.model.account.UserResponse

data class CommentResponse(
    val postId: Long,
    val commenterId: Long,
    val date: String,
    val content: String,
    val commentId: Long,
    val commenter: UserResponse,
)
