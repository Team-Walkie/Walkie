package com.whyranoid.presentation.model.running

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.model.running.RunningPosition

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

fun RunningHistory.toRunningHistoryUiModel(context: Context): RunningHistoryUiModel {
    return RunningHistoryUiModel(
        this.id,
        this.finishedAt,
        if (bitmap == null) {
            null
        } else {
            val result = kotlin.runCatching {
                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            context.contentResolver,
                            Uri.parse(bitmap),
                        ),
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        Uri.parse(bitmap),
                    )
                }
            }
            result.getOrNull()
        },
        this.runningData.distance,
        this.runningData.pace,
        this.runningData.totalRunningTime,
        this.runningData.calories,
        this.runningData.steps,
        this.runningData.paths,
    )
}
