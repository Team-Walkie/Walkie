package com.whyranoid.data.model.running

data class LikeTotalResponse(
    val senderId: Long,
    val receiverId: Long,
    val likeCount: Long,
    val likerProfiles: List<LikerProfile>,
)

data class LikerProfile(
    val walkieId: Long,
    val nickName: String,
    val profileImg: String,
    val status: String,
)
