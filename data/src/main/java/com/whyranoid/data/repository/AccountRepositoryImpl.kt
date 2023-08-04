package com.whyranoid.data.repository

import com.whyranoid.data.AccountDataStore
import com.whyranoid.domain.model.account.Sex
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(
    private val accountDataStore: AccountDataStore,
) : AccountRepository {

    override val authId: Flow<String?> = accountDataStore.authId
    override val uId: Flow<Long?> = accountDataStore.uId

    // TODO API Call
    override suspend fun signUp(
        uid: Long,
        authId: String,
        userName: String,
        profileUrl: String?,
        nickName: String,
        birthDay: String,
        phoneNumber: String,
        sex: Sex,
        height: Int,
        weight: Int,
        agreeGps: Boolean,
        agreeSubscription: Boolean,
    ): Result<Boolean> {
        return kotlin.runCatching {
            accountDataStore.updateUId(uid)
            accountDataStore.updateAuthId(authId)
            accountDataStore.updateUserName(userName)
            accountDataStore.updateNickName(nickName)
            profileUrl?.let { url -> accountDataStore.updateProfileUrl(url) }
            true
        }
    }

    override suspend fun signIn(): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun singOut(): Result<Boolean> {
        return Result.success(true)
    }
}
