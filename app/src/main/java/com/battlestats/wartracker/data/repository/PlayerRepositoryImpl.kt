package com.battlestats.wartracker.data.repository

import android.util.Log
import com.battlestats.wartracker.data.local.dao.PlayerDao
import com.battlestats.wartracker.data.local.model.PlayerEntity
import com.battlestats.wartracker.data.local.model.toEntity
import com.battlestats.wartracker.data.remote.model.PlayerDto
import com.battlestats.wartracker.domain.repository.PlayerRepository
import com.br.moviefy.data.network.RetrofitService
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class PlayerRepositoryImpl(private val apiService: RetrofitService,
                           private val playerDao: PlayerDao
    ): PlayerRepository {


    override suspend fun getPlayerDetails(playerTag: String): PlayerDto? {
        return try {
            val response = apiService.service.getPlayerDetails(playerTag)
            Log.d("PlayerRepositoryImpl", "Jogador encontrado: $response")
            response
        }catch (e : Exception) {
            Log.e("PlayerRepositoryImpl", "Erro HTTP: ${e.cause} - ${e.message}")
            null
        } catch (e: IOException) {
            Log.e("PlayerRepositoryImpl", "Erro de conexão: ${e.message}")
            null
        }catch (e: Exception) {
            Log.e("PlayerRepositoryImpl", "Erro desconhecido: ${e.localizedMessage}")
            null

        }
    }

    override suspend fun insert(playerDto: PlayerDto) {
        playerDao.insertPlayer(playerDto.toEntity())
    }

    override suspend fun delete(player: PlayerEntity) {
        playerDao.deletePlayer(player)
    }

    override suspend fun searchByName(name: String): List<PlayerEntity> {
        return playerDao.searchPlayersByName(name)
    }

    override suspend fun getByTag(tag: String): PlayerEntity? {
        return playerDao.getPlayerByTag(tag)
    }

    override suspend fun getAllPlayers(): Flow<List<PlayerEntity>> {
        return playerDao.getAllPlayers()
    }


}