package com.whyranoid.data.datasource

import android.util.Log
import com.whyranoid.data.AccountDataStore
import com.whyranoid.data.datasource.community.CommunityService
import com.whyranoid.domain.datasource.UserDataSource
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.model.user.UserDetail
import kotlinx.coroutines.flow.first

class UserDataSourceImpl(
    private val dataStore: AccountDataStore,
    private val communityService: CommunityService,
) : UserDataSource {
    // TODO: change to api call
    override suspend fun getUser(uid: Long): Result<User> {
        val savedUId = dataStore.uId.first()
        return if (savedUId == uid) {
            kotlin.runCatching {
                val name = dataStore.userName.first()
                val nickName = dataStore.nickName.first()
                val imageUrl = dataStore.profileUrl.first()
                User(
                    savedUId,
                    requireNotNull(name),
                    requireNotNull(nickName),
                    requireNotNull(imageUrl),
                )
            }
        } else {
            kotlin.runCatching {
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
    }

    // TODO: change to api call
    override suspend fun getUserDetail(uid: Long): Result<UserDetail> {
        return Result.success(UserDetail.DUMMY)
    }
}
