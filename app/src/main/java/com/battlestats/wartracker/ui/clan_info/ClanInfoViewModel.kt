package com.battlestats.wartracker.ui.clan_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.battlestats.wartracker.domain.usecase.GetClanDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.battlestats.wartracker.domain.util.Result


class ClanInfoViewModel(
     private val getClanDetailsUseCase: GetClanDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClanInfoUiState())
    val uiState = _uiState.asStateFlow()

    fun loadClanDetails(clanTag: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = getClanDetailsUseCase(clanTag)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, clanDetails = result.data)
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

    fun onEvent(event: ClanInfoUiEvent) {
        when (event) {
            is ClanInfoUiEvent.OnMemberClicked -> {
                // TODO: Lógica para navegar para o perfil do membro clicado
            }
            // O OnBackClicked será tratado na UI com o NavController
            else -> {}
        }
    }
}