package com.whyranoid.domain.repository

import com.whyranoid.domain.model.running.UserLocation
import kotlinx.coroutines.flow.StateFlow

interface RunningRepository {

    val userLocationState: StateFlow<UserLocation>

    suspend fun startRunning()
    suspend fun pauseRunning()
    suspend fun resumeRunning()
    suspend fun finishRunning()
    fun listenLocation()

    fun removeListener()
}
