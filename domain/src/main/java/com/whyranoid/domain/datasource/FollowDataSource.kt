package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.user.User

interface FollowDataSource {
    suspend fun follow(followerId: Long, followedId: Long): Result<Long>

    suspend fun unfollow(followerId: Long, followedId: Long): Result<Long>

    suspend fun getWalkingFollowingList(uid: Long): Result<List<User>>

    suspend fun getFollowingList(uid: Long): Result<List<User>>

    suspend fun getFollowerList(uid: Long): Result<List<User>>
}
