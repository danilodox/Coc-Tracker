package com.battlestats.wartracker.ui.player_profile

import com.battlestats.wartracker.data.remote.model.PlayerDto

data class PlayerProfileUiState (
    val isLoading: Boolean = false,
    val playerDto: PlayerDto? = null,
    val isError: Boolean = false,
    val isFavorite: Boolean = false
    )
