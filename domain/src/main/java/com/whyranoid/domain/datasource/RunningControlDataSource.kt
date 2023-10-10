package com.whyranoid.domain.datasource

import com.whyranoid.domain.model.user.User

interface RunningControlDataSource {
    suspend fun runningStart(id: Long): Result<Long>

    suspend fun runningFinish(id: Long): Result<Unit>

    suspend fun sendLike(id: Long, receiverId: Long): Result<Long>

    suspend fun getTotalLiker(
        id: Long,
        authId: String,
    ): Result<List<User>>

    suspend fun getLikeCount(
        id: Long,
        authId: String,
    ): Result<Long>
}
