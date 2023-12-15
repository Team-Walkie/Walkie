package com.whyranoid.data.model.follow

data class FollowRequest(
    val followerId: Long,
    val followedId: Long,
)
