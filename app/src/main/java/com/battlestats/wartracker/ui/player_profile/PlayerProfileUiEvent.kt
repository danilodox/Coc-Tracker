package com.battlestats.wartracker.ui.player_profile

import com.battlestats.wartracker.data.remote.model.PlayerDto

sealed class PlayerProfileUiEvent {
    data class ShowToast(val message: String) : PlayerProfileUiEvent()
    data object NavigateToNextScreen : PlayerProfileUiEvent()
    data class OnFavoriteClick(val playerDto: PlayerDto) : PlayerProfileUiEvent()
    // Adicione outros eventos conforme necessário
}