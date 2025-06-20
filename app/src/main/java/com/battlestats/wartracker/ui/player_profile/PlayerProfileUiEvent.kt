package com.battlestats.wartracker.ui.player_profile

sealed class PlayerProfileUiEvent {
    data class ShowToast(val message: String) : PlayerProfileUiEvent()
    object NavigateToNextScreen : PlayerProfileUiEvent()
    // Adicione outros eventos conforme necessário
}