package com.whyranoid.data.datasource.account

import com.whyranoid.data.getResult
import com.whyranoid.data.model.account.SignUpRequest
import com.whyranoid.data.model.account.toLoginData
import com.whyranoid.domain.datasource.AccountDataSource
import com.whyranoid.domain.model.account.LoginData

class AccountDataSourceImpl(private val accountService: AccountService) : AccountDataSource {
    override suspend fun signUp(
        nickName: String,
        profileUrl: String?,
        authId: String,
        agreeGps: Boolean,
        agreeMarketing: Boolean,
    ): Result<Long> {
        return kotlin.runCatching {
            val request = SignUpRequest(
                userName = nickName,
                profileImg = profileUrl ?: "",
                authId = authId,
                agreeGps = agreeGps,
                agreeSubscription = agreeMarketing,
            )

            val response = accountService.signUp(request)

            if (response.isSuccessful.not()) {
                throw Exception(response.errorBody().toString())
            } else if (response.body() == null) throw Exception(response.message())
            requireNotNull(response.body()?.walkieId?.toLong() ?: throw Exception("empty response"))
        }
    }

    override suspend fun nickCheck(nickName: String): Result<Pair<Boolean, String>> {
        return kotlin.runCatching {
            val response = accountService.checkNickName(nickName)
            if (response.isSuccessful.not()) {
                throw Exception(response.errorBody().toString())
            } else if (response.body() == null) throw Exception(response.message())
            requireNotNull(response.body()).let { Pair(it.isDuplicated, it.nickName ?: "empty") }
        }
    }

    override suspend fun signIn(authorId: String): Result<LoginData> {
        return kotlin.runCatching {
            val response = accountService.login(authorId)
            response.getResult {
                it.toLoginData()
            }
        }
    }
}
