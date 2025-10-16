package com.battlestats.wartracker.domain.usecase

import com.battlestats.wartracker.domain.model.Player
import com.battlestats.wartracker.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritePlayersUseCase(private val repository: PlayerRepository) { // Use a interface do domain
    //operator fun invoke(): Flow<List<Player>> { // Retorna Flow de Player (domain)
       // return repository.getAllFavoritePlayers()
    //}
}
