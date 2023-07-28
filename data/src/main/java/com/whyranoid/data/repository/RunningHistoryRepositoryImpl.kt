package com.whyranoid.data.repository

import com.google.gson.Gson
import com.whyranoid.data.datasource.runninghistory.RunningHistoryDao
import com.whyranoid.data.model.RunningHistoryEntity
import com.whyranoid.domain.model.running.RunningData
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.model.running.RunningPosition
import com.whyranoid.domain.repository.RunningHistoryRepository

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
            runningHistoryDao.getAll().map {
                RunningHistory(
                    it.id,
                    RunningData(
                        it.distance,
                        it.pace,
                        it.totalRunningTime,
                        it.calories,
                        it.steps,
                        gson.fromJson(it.paths, Array<Array<RunningPosition>>::class.java)
                            .map { array ->
                                array.toList()
                            }.toList(),
                    ),
                    it.finishedAt,
                    it.bitmap,
                )
            }
        }
    }
}
