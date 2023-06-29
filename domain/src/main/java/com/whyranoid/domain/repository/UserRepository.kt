package com.whyranoid.domain.repository

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail

interface UserRepository {
    suspend fun getUser(uid: String): Result<User>

    suspend fun getUserDetail(uid: String): Result<UserDetail>
}
