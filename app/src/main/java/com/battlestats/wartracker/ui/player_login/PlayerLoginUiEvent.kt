package com.battlestats.wartracker.ui.player_login

sealed class PlayerLoginUiEvent {
    data class ShowToast(val message: String): PlayerLoginUiEvent()
    data class NavigateToHome(val tag: String): PlayerLoginUiEvent()
}