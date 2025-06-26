package com.battlestats.wartracker.ui.player_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlestats.wartracker.data.local.repository.LocalPlayerRepository
import com.battlestats.wartracker.data.model.Player
import com.battlestats.wartracker.data.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerProfileViewModel(
    private val repository: PlayerRepository,
    private val localRepository: LocalPlayerRepository
) :ViewModel() {

    private val _uiState = MutableStateFlow(PlayerProfileUiState())
    val uiState: StateFlow<PlayerProfileUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PlayerProfileUiEvent>()
    val uiEvent: SharedFlow<PlayerProfileUiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: PlayerProfileUiEvent) {
        when(event) {
            /*is PlayerProfileUiEvent.OnBackClick -> {
                viewModelScope.launch {
                    _uiEvent.emit(PlayerProfileUiEvent.NavigateToNextScreen)
                }
            }*/
            is PlayerProfileUiEvent.OnFavoriteClick -> {

                _uiState.update { it.copy(isFavorite = !_uiState.value.isFavorite) }

                viewModelScope.launch {
                    try {
                        event.player.let {
                            localRepository.insert(it)
                            _uiEvent.emit(PlayerProfileUiEvent.ShowToast("Jogador salvo como favorito"))
                        }

                    } catch (e: Exception) {
                        _uiEvent.emit(PlayerProfileUiEvent.ShowToast("Erro ao salvar favorito."))
                    }
                }


            }
            is PlayerProfileUiEvent.ShowToast -> {

            }
            is PlayerProfileUiEvent.NavigateToNextScreen -> {

            }
        }
    }

    fun getPlayerData(playerTag: String) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, isError = false) }

            try {
                val player = repository.getPlayerDetails(playerTag)

                if (player != null) {
                    _uiState.update { it.copy(player = player, isLoading = false) }
                    _uiEvent.emit(PlayerProfileUiEvent.NavigateToNextScreen)
                    _uiEvent.emit(PlayerProfileUiEvent.ShowToast("Player loaded successfully"))
                } else {
                    _uiState.update { it.copy(isError = true, isLoading = false) }
                    _uiEvent.emit(PlayerProfileUiEvent.ShowToast("Player not found"))
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isError = true, isLoading = false) }
                _uiEvent.emit(PlayerProfileUiEvent.ShowToast("Error loading player: ${e.localizedMessage}"))
            }
        }
    }

    fun calculateTownHallProgress(
        maxHeroes: Int, upgradedHeroes: Int,
        maxBuildings: Int, upgradedBuildings: Int,
        maxDefenses: Int, upgradedDefenses: Int,
        maxTroops: Int, upgradedTroops: Int,
        maxWalls: Int, upgradedWalls: Int
    ): Float {
        val heroProgress = if (maxHeroes > 0) upgradedHeroes.toFloat() / maxHeroes else 0f
        val buildingProgress = if (maxBuildings > 0) upgradedBuildings.toFloat() / maxBuildings else 0f
        val defenseProgress = if (maxDefenses > 0) upgradedDefenses.toFloat() / maxDefenses else 0f
        val troopProgress = if (maxTroops > 0) upgradedTroops.toFloat() / maxTroops else 0f
        val wallProgress = if (maxWalls > 0) upgradedWalls.toFloat() / maxWalls else 0f

        val totalProgress = (heroProgress * 0.25f) +
                (buildingProgress * 0.25f) +
                (defenseProgress * 0.2f) +
                (troopProgress * 0.2f) +
                (wallProgress * 0.1f)

        return (totalProgress * 100).coerceIn(0f, 100f)
    }


}