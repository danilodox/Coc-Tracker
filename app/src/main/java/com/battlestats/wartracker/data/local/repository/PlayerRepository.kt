package com.battlestats.wartracker.data.local.repository

import com.battlestats.wartracker.data.local.dao.PlayerDao
import com.battlestats.wartracker.data.local.model.PlayerEntity
import com.battlestats.wartracker.data.local.model.toEntity
import com.battlestats.wartracker.data.model.Player

class LocalPlayerRepository(private val dao: PlayerDao) {

    suspend fun insert(player: Player) {
        dao.insertPlayer(player.toEntity())
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
}