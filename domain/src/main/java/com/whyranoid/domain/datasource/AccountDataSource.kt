package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.account.LoginData

interface AccountDataSource {
    suspend fun signUp(
        nickName: String,
        profileUrl: String?,
        authId: String,
        agreeGps: Boolean,
        agreeMarketing: Boolean,
    ): Result<Long>

    suspend fun nickCheck(nickName: String): Result<Pair<Boolean, String>>

    suspend fun signIn(authorId: String): Result<LoginData>

    suspend fun changeMyInfo(walkieId: Long, nickName: String, profileUrl: String?): Result<Boolean>
}
