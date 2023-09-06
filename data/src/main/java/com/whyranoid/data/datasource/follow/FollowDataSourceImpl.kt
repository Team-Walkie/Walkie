package com.whyranoid.data.datasource.follow

import com.whyranoid.data.model.follow.FollowRequest
import com.whyranoid.domain.datasource.FollowDataSource
import com.whyranoid.domain.model.user.User

class FollowDataSourceImpl(private val followService: FollowService) : FollowDataSource {
    override suspend fun follow(followerId: Long, followedId: Long): Result<Long> {
        return kotlin.runCatching {
            val request = FollowRequest(followerId, followedId, false)
            val response = followService.follow(request)
            requireNotNull(response.body()).followedId
        }
    }

    override suspend fun unfollow(followerId: Long, followedId: Long): Result<Long> {
        return kotlin.runCatching {
            val request = FollowRequest(followerId, followedId, true)
            val response = followService.unfollow(request)
            requireNotNull(response.body()).followedId
        }
    }

    override suspend fun getWalkingFollowingList(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val list = requireNotNull(followService.getWalkingFollowingList(uid).body()).list
            list.map {
                User(it.walkieId, "name", it.nickname, it.profileImg)
            }
        }
    }

    override suspend fun getFollowingList(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val list = requireNotNull(followService.getFollowingList(uid).body()).list
            list.map {
                User(it.walkieId, "name", it.nickname, it.profileImg)
            }
        }
    }

    override suspend fun getFollowerList(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val list = requireNotNull(followService.getFollowerList(uid).body()).list
            list.map {
                User(it.walkieId, "name", it.nickname, it.profileImg)
            }
        }
    }
}
