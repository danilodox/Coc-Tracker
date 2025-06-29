package com.battlestats.wartracker.ui.favorite_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.battlestats.wartracker.data.local.model.PlayerEntity
import com.battlestats.wartracker.data.local.repository.LocalPlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritePlayersViewModel(
    private val localRepository: LocalPlayerRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<PlayerEntity>>(emptyList())
    val players: StateFlow<List<PlayerEntity>> = _players

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