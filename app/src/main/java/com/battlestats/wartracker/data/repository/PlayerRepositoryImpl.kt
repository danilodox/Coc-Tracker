package com.battlestats.wartracker.data.repository

import android.util.Log
import com.battlestats.wartracker.data.local.dao.PlayerDao
import com.battlestats.wartracker.data.local.model.PlayerEntity
import com.battlestats.wartracker.data.local.model.toDomain
import com.battlestats.wartracker.data.local.model.toEntity
import com.battlestats.wartracker.data.remote.model.PlayerDto
import com.battlestats.wartracker.domain.model.Player
import com.battlestats.wartracker.domain.repository.PlayerRepository
import com.br.moviefy.data.network.ApiService
import com.br.moviefy.data.network.RetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class PlayerRepositoryImpl(
    private val apiService: ApiService, // Alterado para ApiService
    private val playerDao: PlayerDao
) : PlayerRepository { // Implementa a interface do domínio

    /**
     * CORREÇÃO 1: Retorna 'Player' (do domínio), não 'PlayerDto'.
     * Mapeia o resultado da API antes de retornar.
     */
    override suspend fun getPlayerDetails(playerTag: String): Player? {
        return try {
            val playerDto = apiService.getPlayerDetails(playerTag)
            Log.d("PlayerRepositoryImpl", "Jogador encontrado: $playerDto")
            // Mapeia o DTO para o modelo de domínio
            playerDto.toDomain()
        } catch (e: IOException) {
            Log.e("PlayerRepositoryImpl", "Erro de conexão: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("PlayerRepositoryImpl", "Erro desconhecido: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun insert(player: Player) {
        playerDao.insertPlayer(player.toEntity())
    }

    override suspend fun delete(player: Player) {
        playerDao.deletePlayer(player.toEntity())
    }

    override suspend fun searchByName(name: String): List<Player> {
        return playerDao.searchPlayersByName(name).map { it.toDomain() }
    }

    override suspend fun getByTag(tag: String): Player? {
        val entity = playerDao.getPlayerByTag(tag)
        return entity?.toDomain()
    }

    override suspend fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers().map { listOfEntities ->
            listOfEntities.map { it.toDomain() }
        }
    }

    override suspend fun getAllFavoritePlayers(): Flow<List<Player>> {
        return playerDao.getAllPlayers().map { listOfEntities ->
            listOfEntities.map { it.toDomain() }
        }
    }

    override suspend fun getFavoritePlayerByTag(tag: String): Player? {
        val playerEntity = playerDao.getPlayerByTag(tag)
        return playerEntity?.toDomain()
    }

    override suspend fun saveFavoritePlayer(player: Player) {
        playerDao.insertPlayer(player.toEntity())
    }

    override suspend fun removeFavoritePlayer(player: Player) {
        playerDao.deletePlayer(player.toEntity())
    }
}