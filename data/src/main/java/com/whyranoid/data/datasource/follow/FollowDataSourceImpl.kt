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
            val response = followService.follow(request)
            requireNotNull(response.body()).followedId
        }
    }

    override suspend fun getWalkingFollowings(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val list = requireNotNull(followService.getWalkingFollowings(uid).body()).list
            list.map {
                User(it.walkieId, "name", it.nickname, it.profileImg)
            }
        }
    }

    override suspend fun getFollowings(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val list = requireNotNull(followService.getFollowings(uid).body()).list
            list.map {
                User(it.walkieId, "name", it.nickname, it.profileImg)
            }
        }
    }

    override suspend fun getFollowers(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val list = requireNotNull(followService.getFollowers(uid).body()).list
            list.map {
                User(it.walkieId, "name", it.nickname, it.profileImg)
            }
        }
    }
}
