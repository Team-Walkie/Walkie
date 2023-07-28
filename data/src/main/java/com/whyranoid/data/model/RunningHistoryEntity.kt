package com.whyranoid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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
