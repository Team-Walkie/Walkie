package com.whyranoid.data.model.account

import com.google.gson.annotations.SerializedName
import com.whyranoid.domain.model.user.User

data class UserResponse(
    @SerializedName("walkieId") val uid: Long,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String,
    @SerializedName("status") val status: String,
) {
    fun toUser(): User {
        return User(
            uid,
            status,
            nickname,
            profileImg,
        )
    }
}
