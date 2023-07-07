package com.whyranoid.data.repository

import com.whyranoid.domain.repository.RunningRepository
import com.whyranoid.runningdata.RunningDataManager

class RunningRepositoryImpl : RunningRepository {

    private val runningDataManager = RunningDataManager.getInstance()

    override suspend fun startRunning() {
    }

    override suspend fun pauseRunning() {
        TODO("Not yet implemented")
    }

    override suspend fun resumeRunning() {
        TODO("Not yet implemented")
    }

    override suspend fun finishRunning() {
        TODO("Not yet implemented")
    }
}
