package com.whyranoid.data.repository

import com.whyranoid.data.AccountDataStore
import com.whyranoid.domain.model.account.Sex
import com.whyranoid.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class AccountRepositoryImpl(
    private val accountDataStore: AccountDataStore,
) : AccountRepository {

    override val authId: Flow<String?> = accountDataStore.authId

    // TODO API Call
    override suspend fun signUp(
        userName: String,
        uid: String,
        profileUrl: String,
        birthDay: String,
        phoneNumber: String,
        sex: Sex,
        height: Int,
        weight: Int,
        authId: String,
        agreeGps: Boolean,
        agreeSubscription: Boolean,
    ): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun signIn(): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun singOut(): Result<Boolean> {
        return Result.success(true)
    }
}
