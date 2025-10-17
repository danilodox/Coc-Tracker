package com.battlestats.wartracker.ui.player_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlestats.wartracker.domain.util.Result
import com.battlestats.wartracker.domain.usecase.CalculateTownHallProgressUseCase
import com.battlestats.wartracker.domain.usecase.GetPlayerProfileUseCase
import com.battlestats.wartracker.domain.usecase.ToggleFavoriteStatusUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerProfileViewModel(
    private val getPlayerProfileUseCase: GetPlayerProfileUseCase,
    private val toggleFavoriteStatusUseCase: ToggleFavoriteStatusUseCase,
   // private val calculateTownHallProgressUseCase: CalculateTownHallProgressUseCase
) :ViewModel() {

    private val _uiState = MutableStateFlow(PlayerProfileUiState())
    val uiState: StateFlow<PlayerProfileUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PlayerProfileUiEvent>(replay = 1)
    val uiEvent: SharedFlow<PlayerProfileUiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: PlayerProfileUiEvent) {
        when(event) {
            is PlayerProfileUiEvent.OnFavoriteClick -> {

                viewModelScope.launch {
                    val newFavoriteState = toggleFavoriteStatusUseCase(event.player)
                    _uiState.update { it.copy(isFavorite = newFavoriteState) }
                    val message = if (newFavoriteState) "Salved as favorite" else "Removed from favorites"
                    _uiEvent.emit(PlayerProfileUiEvent.ShowToast(message))
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

            when (val result = getPlayerProfileUseCase(playerTag)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            player = result.data?.player,
                            isFavorite = result.data?.isFavorite ?: false
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isError = true, isLoading = false) }
                    _uiEvent.emit(PlayerProfileUiEvent.ShowToast(result.message ?: "Error"))
                }

                else -> {}
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