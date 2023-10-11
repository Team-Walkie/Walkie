package com.whyranoid.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whyranoid.data.datasource.running.RunningHistoryDao
import com.whyranoid.data.model.running.RunningHistoryEntity

@Database(entities = [RunningHistoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun runningHistoryDao(): RunningHistoryDao
}
