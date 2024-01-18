package com.whyranoid.data.model.account

import com.whyranoid.domain.model.account.LoginData

data class LoginDataResponse(
    val walkieId: Long,
    val nickname: String,
    val profileImg: String,
)

fun LoginDataResponse.toLoginData() = LoginData(
    walkieId = walkieId,
    nickname = nickname,
    profileImg = profileImg,
)