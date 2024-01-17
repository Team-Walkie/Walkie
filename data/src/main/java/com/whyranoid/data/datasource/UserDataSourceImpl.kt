package com.whyranoid.data.datasource

import android.util.Log
import com.whyranoid.data.datasource.community.CommunityService
import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail

class UserDataSourceImpl(
    private val communityService: CommunityService,
) : UserDataSource {
    override suspend fun getUser(uid: Long): Result<User> {
        return kotlin.runCatching {
            val response = requireNotNull(communityService.getUserNickProfile(uid).body())
            User(
                uid,
                requireNotNull(response.nickname),
                requireNotNull(response.nickname),
                requireNotNull(response.profileImg),
            )
        }.onFailure {
            Log.d("UserDataSourceImpl", it.message.toString())
        }
    }

    // TODO: change to api call
    override suspend fun getUserDetail(uid: Long): Result<UserDetail> {
        return Result.success(UserDetail.DUMMY)
    }
}
