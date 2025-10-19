package com.battlestats.wartracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.battlestats.wartracker.data.local.model.ClanEntity

@Dao
interface ClanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClan(clan: ClanEntity)
}