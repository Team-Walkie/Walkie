package com.whyranoid.data.datasource.running

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.whyranoid.data.model.RunningHistoryEntity

@Dao
interface RunningHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(runningHistoryEntity: RunningHistoryEntity)

    @Query("SELECT * FROM running_history")
    fun getAll(): List<RunningHistoryEntity>
}
