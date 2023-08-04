package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail

interface UserDataSource {
    suspend fun getUser(uid: Long): Result<User>

    suspend fun getUserDetail(uid: Long): Result<UserDetail>
}
