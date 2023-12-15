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
            val response = followService.getWalkingFollowings(uid)
            val followingList = requireNotNull(response.body())
            followingList.map { it.toUser() }
        }
    }

    override suspend fun getFollowings(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val response = followService.getFollowings(uid)
            val followingList = requireNotNull(response.body())
            followingList.map { it.toUser() }
        }
    }

    override suspend fun getFollowers(uid: Long): Result<List<User>> {
        return kotlin.runCatching {
            val response = followService.getFollowers(uid)
            val followerList = requireNotNull(response.body())
            followerList.map { it.toUser() }
        }
    }

    override suspend fun searchNickname(keyword: String): Result<List<User>> {
        return kotlin.runCatching {
            val response = followService.searchNickname(keyword)
            val userList = requireNotNull(response.body())
            userList.map { it.toUser() }
        }
    }
}
