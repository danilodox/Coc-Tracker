package com.battlestats.wartracker.ui.favorite_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlestats.wartracker.data.local.model.PlayerEntity
import com.battlestats.wartracker.domain.model.Player
import com.battlestats.wartracker.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritePlayersViewModel(
    private val localRepository: PlayerRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            localRepository.getAllPlayers()
                .collect { favoritePlayers ->
                    _players.value = favoritePlayers
                }
        }
    }
}