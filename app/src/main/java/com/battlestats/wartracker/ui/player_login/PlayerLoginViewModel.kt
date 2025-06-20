package com.battlestats.wartracker.ui.player_login

import androidx.lifecycle.ViewModel
import com.battlestats.wartracker.data.network.interceptor.TokenProvider
import com.battlestats.wartracker.data.repository.PlayerRepository

class PlayerLoginViewModel(
    private val repository: PlayerRepository,
    private val tokenProvider: TokenProvider // ainda útil se você quiser guardar outras infos
) : ViewModel() {

}