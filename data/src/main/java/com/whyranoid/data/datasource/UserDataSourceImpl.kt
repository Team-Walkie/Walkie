package com.whyranoid.data.datasource

import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail

class UserDataSourceImpl : UserDataSource {
    // TODO: change to api call
    override suspend fun getUser(uid: String): Result<User> {
        return Result.success(User.DUMMY)
    }

    // TODO: change to api call
    override suspend fun getUserDetail(uid: String): Result<UserDetail> {
        return Result.success(UserDetail.DUMMY)
    }
}
