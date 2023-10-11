package com.whyranoid.data.model.running

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.whyranoid.domain.model.running.RunningData
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.model.running.RunningPosition

@Entity(tableName = "running_history")
data class RunningHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val distance: Double,
    val pace: Double,
    val totalRunningTime: Int,
    val calories: Int,
    val steps: Int,
    val paths: String,
    val finishedAt: Long,
    val bitmap: String?,
)

fun RunningHistoryEntity.toRunningHistory(gson: Gson): RunningHistory {
    return RunningHistory(
        id,
        RunningData(
            distance,
            pace,
            totalRunningTime,
            calories,
            steps,
            gson.fromJson(paths, Array<Array<RunningPosition>>::class.java)
                .map { array ->
                    array.toList()
                }.toList(),
        ),
        finishedAt,
        bitmap,
    )
}
