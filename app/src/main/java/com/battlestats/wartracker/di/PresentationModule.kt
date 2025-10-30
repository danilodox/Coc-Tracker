package com.battlestats.wartracker.di

import com.battlestats.wartracker.ui.clan_info.ClanInfoViewModel
import com.battlestats.wartracker.ui.favorite_player.FavoritePlayersViewModel
import com.battlestats.wartracker.ui.player_login.PlayerLoginViewModel
import com.battlestats.wartracker.ui.home.HomeViewModel
import com.battlestats.wartracker.ui.player_details.PlayerDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { PlayerLoginViewModel(get()) }
    viewModel { FavoritePlayersViewModel(get()) }
    viewModel { ClanInfoViewModel(get()) }
    viewModel { PlayerDetailsViewModel(get(), get()) }
}