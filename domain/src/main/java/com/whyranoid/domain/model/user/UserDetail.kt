package com.whyranoid.domain.model.user

data class UserDetail(
    val user: User,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean,
)
