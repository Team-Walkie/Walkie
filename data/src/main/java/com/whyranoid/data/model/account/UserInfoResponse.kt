package com.whyranoid.data.model.account

import com.whyranoid.domain.model.account.UserInfo

data class UserInfoResponse (
    val name: String,
    val nickname: String,
    val profileImg: String?
)

fun UserInfoResponse.toUserInfo() = UserInfo(
    name = name,
    nickname = nickname,
    profileImg = profileImg
)