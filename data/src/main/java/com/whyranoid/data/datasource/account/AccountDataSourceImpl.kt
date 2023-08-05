package com.whyranoid.data.datasource.account

import com.whyranoid.domain.datasource.AccountDataSource

class AccountDataSourceImpl(private val accountService: AccountService) : AccountDataSource {
    override suspend fun signUp(
        nickName: String,
        profileUrl: String?,
        authId: String,
        agreeGps: Boolean,
        agreeMarketing: Boolean,
    ): Result<Long> {
        return kotlin.runCatching {
            val response =
                accountService.signUp(nickName, profileUrl ?: "", authId, agreeGps, agreeMarketing)
            if (response.isSuccessful.not()) {
                throw Exception(response.errorBody().toString())
            } else if (response.body() == null) throw Exception(response.message())
            requireNotNull(response.body()).uid
        }
    }

    override suspend fun nickCheck(nickName: String): Result<Pair<Boolean, String>> {
        return kotlin.runCatching {
            val response = accountService.checkNickName(nickName)
            if (response.isSuccessful.not()) {
                throw Exception(response.errorBody().toString())
            } else if (response.body() == null) throw Exception(response.message())
            requireNotNull(response.body()).let { Pair(it.isDuplicated, it.nickName) }
        }
    }
}
