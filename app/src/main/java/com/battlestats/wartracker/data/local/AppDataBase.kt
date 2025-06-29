package com.battlestats.wartracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.battlestats.wartracker.data.local.dao.PlayerDao
import com.battlestats.wartracker.data.local.model.PlayerEntity

@Database(entities = [PlayerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
}