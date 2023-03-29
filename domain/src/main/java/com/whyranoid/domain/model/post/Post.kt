package com.whyranoid.domain.model.post

import com.whyranoid.domain.model.user.User

data class Post(
    val id: Long,
    val imageUrl: String,
    val likeCount: Int,
    val contents: String,
    val author: User,
)
