package com.whyranoid.data.datasource.running

import com.whyranoid.data.model.running.RunningStartRequest
import com.whyranoid.data.model.running.SendLikeRequest
import com.whyranoid.domain.datasource.RunningControlDataSource
import com.whyranoid.domain.model.user.User
import java.time.LocalDateTime

class RunningControlDataSourceImpl(private val runningService: RunningService) :
    RunningControlDataSource {
    override suspend fun runningStart(id: Long): Result<Long> {
        return kotlin.runCatching {
            requireNotNull(
                runningService.runningStart(
                    RunningStartRequest(
                        walkieId = id,
                        startTime = LocalDateTime.now().toString(),
                    ),
                ).body(),
            )
        }
    }

    override suspend fun sendLike(id: Long, receiverId: Long): Result<Long> {
        return kotlin.runCatching {
            requireNotNull(
                runningService.sendLike(
                    SendLikeRequest(
                        senderId = id,
                        receiverId = receiverId,
                    ),
                ).body()?.likerProfiles?.size?.toLong(),
            )
        }
    }

    override suspend fun getTotalLiker(id: Long, authId: String): Result<List<User>> {
        return kotlin.runCatching {
            requireNotNull(
                runningService.getTotalLiker(id, authId).body()?.likerProfiles?.map { it.toUser() },
            )
        }
    }

    override suspend fun getLikeCount(id: Long, authId: String): Result<Long> {
        return kotlin.runCatching {
            requireNotNull(
                runningService.getLikeCount(id, authId).body(),
            )
        }
    }
}
