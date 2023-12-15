package com.whyranoid.data.repository

import com.whyranoid.domain.datasource.FollowDataSource
import com.whyranoid.domain.model.user.User
import com.whyranoid.domain.repository.FollowRepository

class FollowRepositoryImpl(private val followDataSource: FollowDataSource) : FollowRepository {
    override suspend fun follow(followerId: Long, followedId: Long): Result<Long> {
        return followDataSource.follow(followerId, followedId)
    }

    override suspend fun unfollow(followerId: Long, followedId: Long): Result<Long> {
        return followDataSource.unfollow(followerId, followedId)
    }

    override suspend fun getWalkingFollowings(uid: Long): Result<List<User>> {
        return followDataSource.getWalkingFollowings(uid)
    }

    override suspend fun getFollowings(uid: Long): Result<List<User>> {
        return followDataSource.getFollowings(uid)
    }

    override suspend fun getFollowers(uid: Long): Result<List<User>> {
        return followDataSource.getFollowers(uid)
    }

    override suspend fun searchNickname(keyword: String): Result<List<User>> {
        return followDataSource.searchNickname(keyword)
    }
}
