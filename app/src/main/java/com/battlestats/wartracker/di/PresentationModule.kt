package com.battlestats.wartracker.di

import com.battlestats.wartracker.ui.favorite_player.FavoritePlayersViewModel
import com.battlestats.wartracker.ui.player_login.PlayerLoginViewModel
import com.battlestats.wartracker.ui.player_profile.PlayerProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { PlayerProfileViewModel(get()) }
    viewModel { PlayerLoginViewModel(get(), get()) }
    viewModel { FavoritePlayersViewModel(get()) }
}