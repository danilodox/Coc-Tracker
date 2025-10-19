package com.battlestats.wartracker.ui.clan_info

import com.battlestats.wartracker.domain.model.ClanDetails

data class ClanInfoUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val clanDetails: ClanDetails? = null
)