package com.whyranoid.data.model.post

data class SendCommentRequest(
    val postId: Long,
    val commenterId: Long,
    val date: String,
    val content: String,
)
