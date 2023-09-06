package com.whyranoid.data.model.follow

data class FollowersResponse(
    val list: List<FollowerResponse>,
)

data class FollowerResponse(
    val walkieId: Long,
    val nickname: String,
    val profileImg: String,
    val status: String,
)
