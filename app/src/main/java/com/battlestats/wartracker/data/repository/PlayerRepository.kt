package com.battlestats.wartracker.data.repository

import com.battlestats.wartracker.data.model.Player

interface PlayerRepository {
    suspend fun getPlayerDetails(playerTag: String): Player?
}