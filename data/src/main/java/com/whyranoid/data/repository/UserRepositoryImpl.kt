package com.whyranoid.data.repository

import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail
import com.whyranoid.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUser(uid: Long): Result<User> {
        return userDataSource.getUser(uid)
    }

    override suspend fun getUserDetail(uid: Long): Result<UserDetail> {
        return userDataSource.getUserDetail(uid)
    }
}
