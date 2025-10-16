package com.battlestats.wartracker.domain.repository

import com.battlestats.wartracker.data.local.model.PlayerEntity
import com.battlestats.wartracker.data.local.model.toEntity
import com.battlestats.wartracker.data.remote.model.PlayerDto
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun getPlayerDetails(playerTag: String): PlayerDto?

    suspend fun insert(playerDto: PlayerDto)

    suspend fun delete(player: PlayerEntity)

    suspend fun searchByName(name: String): List<PlayerEntity>

    suspend fun getByTag(tag: String): PlayerEntity?

    suspend fun getAllPlayers(): Flow<List<PlayerEntity>>
}