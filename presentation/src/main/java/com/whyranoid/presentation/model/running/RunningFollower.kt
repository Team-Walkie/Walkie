package com.whyranoid.presentation.model.running

import com.whyranoid.domain.model.user.User

data class RunningFollower(
    val user: User,
    val isLiked: Boolean = false,
)
