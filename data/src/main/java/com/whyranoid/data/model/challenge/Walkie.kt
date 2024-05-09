package com.whyranoid.data.model.challenge

import com.whyranoid.domain.model.user.User

data class Walkie(
    val authId: String,
    val profileImg: String,
    val status: String,
    val userId: Int,
    val userName: String
) {
    fun toUser(): User {
        return User(
            uid = userId.toLong(),
            name = userName,
            nickname = userName,
            imageUrl = profileImg
        )
    }
}