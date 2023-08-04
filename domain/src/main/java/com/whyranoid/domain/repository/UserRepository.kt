package com.whyranoid.domain.repository

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail

interface UserRepository {
    suspend fun getUser(uid: Long): Result<User>

    suspend fun getUserDetail(uid: Long): Result<UserDetail>
}
