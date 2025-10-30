package com.battlestats.wartracker.ui.player_details

sealed class PlayerDetailsUiEvent {
    data object OnBackClicked : PlayerDetailsUiEvent()
}