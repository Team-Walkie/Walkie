package com.whyranoid.data.model.running

import com.whyranoid.domain.model.user.User

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
) {
    fun toUser(): User = User(
        uid = this.walkieId,
        name = this.status,
        nickname = this.nickName,
        imageUrl = this.profileImg,
    )
}
