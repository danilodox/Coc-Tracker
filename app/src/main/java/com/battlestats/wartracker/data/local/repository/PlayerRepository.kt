package com.battlestats.wartracker.data.local.repository

import com.battlestats.wartracker.data.local.dao.PlayerDao
import com.battlestats.wartracker.data.local.model.PlayerEntity
import com.battlestats.wartracker.data.local.model.toEntity
import com.battlestats.wartracker.data.remote.model.PlayerDto
import kotlinx.coroutines.flow.Flow

class LocalPlayerRepository(private val dao: PlayerDao) {

    suspend fun insert(playerDto: PlayerDto) {
        dao.insertPlayer(playerDto.toEntity())
    }

    suspend fun delete(player: PlayerEntity) {
        dao.deletePlayer(player)
    }

    suspend fun searchByName(name: String): List<PlayerEntity> {
        return dao.searchPlayersByName(name)
    }

    suspend fun getByTag(tag: String): PlayerEntity? {
        return dao.getPlayerByTag(tag)
    }

    suspend fun getAllPlayers(): Flow<List<PlayerEntity>> {
        return dao.getAllPlayers()
    }
}