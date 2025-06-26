package com.battlestats.wartracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.battlestats.wartracker.data.local.model.PlayerEntity

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE name LIKE '%' || :query || '%'")
    suspend fun searchPlayersByName(query: String): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE tag = :tag")
    suspend fun getPlayerByTag(tag: String): PlayerEntity?

    @Delete
    suspend fun deletePlayer(player: PlayerEntity)
}