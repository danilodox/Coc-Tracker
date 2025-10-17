package com.battlestats.wartracker.domain.usecase

import com.battlestats.wartracker.domain.model.Player
import com.battlestats.wartracker.domain.repository.PlayerRepository

class ToggleFavoriteStatusUseCase(private val repository: PlayerRepository) {
    suspend operator fun invoke(player: Player): Boolean {
        val isCurrentlyFavorite = repository.getFavoritePlayerByTag(player.tag) != null
        return if (isCurrentlyFavorite) {
            repository.removeFavoritePlayer(player)
            false
        } else {
            repository.saveFavoritePlayer(player)
            true
        }
    }
}