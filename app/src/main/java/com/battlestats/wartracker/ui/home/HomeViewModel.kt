package com.battlestats.wartracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlestats.wartracker.data.datastore.SecureTokenProvider
import com.battlestats.wartracker.domain.util.Result
import com.battlestats.wartracker.domain.usecase.GetPlayerProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPlayerProfileUseCase: GetPlayerProfileUseCase,
    private val secureTokenProvider: SecureTokenProvider // Para buscar a tag salva
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialPlayerData()
    }

    private fun loadInitialPlayerData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val playerTag = secureTokenProvider.getSavedTag()

            if (playerTag.isNullOrBlank()) {
                _uiState.update { it.copy(isLoading = false, error = "No player tag found.") }
                return@launch
            }

            when (val result = getPlayerProfileUseCase(playerTag)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            player = result.data?.player,
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }

                else -> {}
            }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.OnViewClanClicked -> { /* TODO: Lógica de navegação para a tela do clã */ }
            HomeUiEvent.OnWarInfoClicked -> { /* TODO: Lógica de navegação para a tela de war info */ }
            HomeUiEvent.OnSearchPlayerClicked -> { /* TODO: Lógica de navegação para a tela de busca */ }
            else -> {}
        }
    }
}