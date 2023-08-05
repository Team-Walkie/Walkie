package com.whyranoid.data.repository

import com.whyranoid.data.AccountDataStore
import com.whyranoid.domain.datasource.AccountDataSource
import com.whyranoid.domain.model.account.Sex
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(
    private val accountDataSource: AccountDataSource,
    private val accountDataStore: AccountDataStore,
) : AccountRepository {

    override val authId: Flow<String?> = accountDataStore.authId
    override val uId: Flow<Long?> = accountDataStore.uId

    // TODO API Call
    override suspend fun signUp(
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
            accountDataSource.signUp(nickName, profileUrl, authId, agreeGps, agreeSubscription)
                .onSuccess { uid ->
                    accountDataStore.updateUId(uid)
                    accountDataStore.updateAuthId(authId)
                    accountDataStore.updateUserName(userName)
                    accountDataStore.updateNickName(nickName)
                    profileUrl?.let { url -> accountDataStore.updateProfileUrl(url) }
                    return@runCatching true
                }
            false
        }
    }

    override suspend fun signIn(): Result<Boolean> {
        return kotlin.runCatching {
            // TODO API CALL and update
            accountDataStore.updateUId(0L)
            true
        }
    }

    override suspend fun singOut(): Result<Boolean> {
        return kotlin.runCatching {
            accountDataStore.removeAll()
            true
        }
    }

    override suspend fun checkNickName(nickName: String): Result<Pair<Boolean, String>> {
        return kotlin.runCatching {
            accountDataSource.nickCheck(nickName).onSuccess {
                return@runCatching it
            }
            Pair(false, "Error")
        }
    }
}
