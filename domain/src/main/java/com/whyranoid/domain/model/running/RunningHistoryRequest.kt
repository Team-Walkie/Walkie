package com.whyranoid.domain.model.running

data class RunningHistoryRequest(
    val runningData: RunningData,
    val finishedAt: Long,
)
