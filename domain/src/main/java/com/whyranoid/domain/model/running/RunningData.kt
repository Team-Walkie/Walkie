package com.whyranoid.domain.model.running

data class RunningData(
    val distance: Double,
    val pace: Double,
    val totalRunningTime: Int,
    val calories: Int,
    val steps: Int,
    val paths: List<List<RunningPosition>>
)
