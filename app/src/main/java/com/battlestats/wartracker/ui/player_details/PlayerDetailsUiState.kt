package com.battlestats.wartracker.ui.player_details

import com.battlestats.wartracker.domain.model.Player

data class PlayerDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val player: Player? = null
)