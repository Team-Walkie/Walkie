package com.whyranoid.domain.model.post

import com.whyranoid.domain.model.user.User

data class Comment(
    val id: Long,
    val author: User,
    val contents: String,
)
