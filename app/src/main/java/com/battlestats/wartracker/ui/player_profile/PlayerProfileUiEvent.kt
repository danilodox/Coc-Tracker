package com.battlestats.wartracker.ui.player_profile

import com.battlestats.wartracker.domain.model.Player


sealed class PlayerProfileUiEvent {
    data class ShowToast(val message: String) : PlayerProfileUiEvent()
    data object NavigateToNextScreen : PlayerProfileUiEvent()
    data class OnFavoriteClick(val player: Player) : PlayerProfileUiEvent()
    // Adicione outros eventos conforme necessário
}