package com.battlestats.wartracker.ui.player_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlestats.wartracker.data.network.interceptor.TokenProvider
import com.battlestats.wartracker.data.repository.PlayerRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerLoginViewModel(
    private val repository: PlayerRepository,
    private val tokenProvider: TokenProvider // ainda útil se você quiser guardar outras infos
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerLoginUiState())
    val uiState: StateFlow<PlayerLoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PlayerLoginUiEvent>()
    val uiEvent: SharedFlow<PlayerLoginUiEvent> = _uiEvent.asSharedFlow()

    fun loginWithTag(playerTag: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }

            try {
                val player = repository.getPlayerDetails(playerTag)
                if (player != null) {
                    tokenProvider.saveTag(playerTag) // salva a tag
                    _uiEvent.emit(PlayerLoginUiEvent.NavigateToHome(playerTag))
                } else {
                    _uiState.update { it.copy(isError = true) }
                    _uiEvent.emit(PlayerLoginUiEvent.ShowToast("Player not found"))
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isError = true) }
                _uiEvent.emit(PlayerLoginUiEvent.ShowToast("Error: ${e.localizedMessage}"))
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

}