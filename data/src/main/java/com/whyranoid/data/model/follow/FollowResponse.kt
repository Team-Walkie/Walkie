package com.whyranoid.data.model.follow

data class FollowResponse(
    val followerId: Long,
    val followedId: Long,
    val unfollow: Boolean,
)
