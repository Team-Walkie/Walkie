package com.whyranoid.data.repository

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.whyranoid.data.datasource.runninghistory.RunningHistoryDao
import com.whyranoid.data.model.RunningHistoryEntity
import com.whyranoid.data.model.toRunningHistory
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.repository.RunningHistoryRepository
import java.text.SimpleDateFormat
import java.util.*

class RunningHistoryRepositoryImpl(
    private val runningHistoryDao: RunningHistoryDao,
    private val gson: Gson,
) :
    RunningHistoryRepository {
    override suspend fun saveRunningHistory(runningHistory: RunningHistory): Result<RunningHistory> {
        return kotlin.runCatching {
            runningHistoryDao.insert(
                RunningHistoryEntity(
                    runningHistory.id,
                    runningHistory.runningData.distance,
                    runningHistory.runningData.pace,
                    runningHistory.runningData.totalRunningTime,
                    runningHistory.runningData.calories,
                    runningHistory.runningData.steps,
                    gson.toJson(runningHistory.runningData.paths),
                    runningHistory.finishedAt,
                    runningHistory.bitmap,
                ),
            )
            runningHistory
        }
    }

    override suspend fun getAll(): Result<List<RunningHistory>> {
        return kotlin.runCatching {
            runningHistoryDao.getAll().map { entity ->
                entity.toRunningHistory(gson)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun getByDate(year: Int, month: Int, day: Int): Result<List<RunningHistory>> {
        val format = SimpleDateFormat("yyyy MM dd")
        return kotlin.runCatching {
            runningHistoryDao.getAll().filter { entity ->
                val (y, m, d) = format.format(Date(entity.finishedAt)).split(' ')
                    .map { it.toIntOrNull() }
                year == y && month == m && day == d
            }.map { entity ->
                entity.toRunningHistory(gson)
            }
        }
    }
}
