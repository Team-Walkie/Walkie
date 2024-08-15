package com.whyranoid.data.model.account

import com.whyranoid.domain.model.account.UserInfo

data class UserInfoResponse (
    val nickname: String,
    val profileImg: String?
)

fun UserInfoResponse.toUserInfo() = UserInfo(
    name = "", // TODO 수정
    nickname = nickname,
    profileImg = profileImg
)