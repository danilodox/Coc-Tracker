package com.battlestats.wartracker.ui.player_profile

import com.battlestats.wartracker.domain.model.Player


data class PlayerProfileUiState (
    val isLoading: Boolean = false,
    val player: Player? = null,
    val isError: Boolean = false,
    val isFavorite: Boolean = false
    )
