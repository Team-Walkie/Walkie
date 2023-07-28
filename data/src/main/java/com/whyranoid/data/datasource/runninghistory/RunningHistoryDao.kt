package com.whyranoid.data.datasource.runninghistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.whyranoid.data.model.RunningHistoryEntity

@Dao
interface RunningHistoryDao {
    @Insert
    fun insert(runningHistoryEntity: RunningHistoryEntity)

    @Query("SELECT * FROM running_history")
    fun getAll(): List<RunningHistoryEntity>
}
