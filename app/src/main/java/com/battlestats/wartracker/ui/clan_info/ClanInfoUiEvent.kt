package com.battlestats.wartracker.ui.clan_info

sealed class ClanInfoUiEvent {
    data object OnBackClicked : ClanInfoUiEvent()
    data class OnMemberClicked(val playerTag: String) : ClanInfoUiEvent()
}