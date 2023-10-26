package com.whyranoid.domain.repository

import com.whyranoid.domain.model.running.UserLocation
import kotlinx.coroutines.flow.StateFlow

interface RunningRepository {

    val userLocationState: StateFlow<UserLocation>

    suspend fun startRunning(uid: Long): Result<Long>
    suspend fun pauseRunning()
    suspend fun resumeRunning()
    suspend fun finishRunning(uid: Long): Result<Unit>
    fun listenLocation()

    fun removeListener()
    fun removeUserLocation()

    suspend fun sendLike(uid: Long, receiverId: Long): Result<Long>
}
