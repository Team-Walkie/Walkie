package com.whyranoid.domain.repository

interface RunningRepository {
    suspend fun startRunning()
    suspend fun pauseRunning()
    suspend fun resumeRunning()
    suspend fun finishRunning()
}
