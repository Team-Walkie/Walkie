package com.whyranoid.presentation.model.running

import android.graphics.Bitmap
import com.google.gson.Gson
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.model.running.RunningPosition
import com.whyranoid.presentation.util.BitmapConverter

data class RunningHistoryUiModel(
    val id: Long,
    val finishedAt: Long,
    val bitmap: Bitmap?,
    val distance: Double,
    val pace: Double,
    val totalRunningTime: Int,
    val calories: Int,
    val steps: Int,
    val paths: List<List<RunningPosition>>,
)

fun RunningHistory.toRunningHistoryUiModel(gson: Gson): RunningHistoryUiModel {
    return RunningHistoryUiModel(
        this.id,
        this.finishedAt,
        BitmapConverter.stringToBitmap(this.bitmap),
        this.runningData.distance,
        this.runningData.pace,
        this.runningData.totalRunningTime,
        this.runningData.calories,
        this.runningData.steps,
        this.runningData.paths,
    )
}
