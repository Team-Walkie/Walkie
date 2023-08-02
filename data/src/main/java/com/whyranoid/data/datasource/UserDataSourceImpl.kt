package com.whyranoid.data.datasource

import com.whyranoid.data.AccountDataStore
import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail
import kotlinx.coroutines.flow.first

class UserDataSourceImpl(private val dataStore: AccountDataStore) : UserDataSource {
    // TODO: change to api call
    override suspend fun getUser(uid: String): Result<User> {
        val accountAuthId = dataStore.authId.first()
        return if (accountAuthId == uid) {
            kotlin.runCatching {
                val name = dataStore.userName.first()
                val nickName = dataStore.nickName.first()
                val imageUrl = dataStore.profileUrl.first()
                User(accountAuthId, name, nickName, imageUrl) // TODO uid 변경 사항 적용
            }
        } else {
            Result.success(User.DUMMY)
        }
    }

    // TODO: change to api call
    override suspend fun getUserDetail(uid: String): Result<UserDetail> {
        return Result.success(UserDetail.DUMMY)
    }
}
