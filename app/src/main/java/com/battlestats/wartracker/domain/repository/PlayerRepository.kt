package com.battlestats.wartracker.domain.repository

import com.battlestats.wartracker.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun getPlayerDetails(playerTag: String): Player?

    suspend fun insert(player: Player)

    suspend fun delete(player: Player)

    suspend fun searchByName(name: String): List<Player>

    suspend fun getByTag(tag: String): Player?

    suspend fun getAllPlayers(): Flow<List<Player>>

    suspend fun getAllFavoritePlayers(): Flow<List<Player>>

    suspend fun getFavoritePlayerByTag(tag: String): Player?

    suspend fun saveFavoritePlayer(player: Player)

    suspend fun removeFavoritePlayer(player: Player)
}