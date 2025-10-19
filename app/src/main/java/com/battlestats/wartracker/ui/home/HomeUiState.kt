package com.battlestats.wartracker.ui.home

import com.battlestats.wartracker.domain.model.Player


data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val player: Player? = null,
)
