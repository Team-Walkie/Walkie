package com.whyranoid.domain.model.running

data class RunningHistory(
    val id: Long,
    val runningData: RunningData,
    val finishedAt: Long,
    val bitmap: String?,
)
