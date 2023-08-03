package com.whyranoid.domain.repository

import com.whyranoid.domain.model.account.Sex
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    val authId: Flow<String?>
    suspend fun signUp(
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
    ): Result<Boolean>

    suspend fun signIn(): Result<Boolean>

    suspend fun singOut(): Result<Boolean>
}