package com.whyranoid.domain.repository

import com.whyranoid.domain.model.running.RunningHistory

interface RunningHistoryRepository {
    suspend fun saveRunningHistory(runningHistory: RunningHistory): Result<RunningHistory>
    suspend fun getAll(): Result<List<RunningHistory>>

    suspend fun getByDate(year: Int, month: Int, day: Int): Result<List<RunningHistory>>
}
