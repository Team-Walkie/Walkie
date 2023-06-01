package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail

interface UserDataSource {
    suspend fun getUser(uid: String): Result<User>

    suspend fun getUserDetail(uid: String): Result<UserDetail>
}
