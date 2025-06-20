package com.battlestats.wartracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.battlestats.wartracker.ui.player_profile.PlayerProfile
import com.battlestats.wartracker.ui.screens.WelcomeLoginScreen
import com.battlestats.wartracker.ui.player_profile.PlayerProfileViewModel

@Composable
fun AppNavigation(navController: NavHostController, playerProfileViewModel: PlayerProfileViewModel) {
    NavHost(navController = navController, startDestination = "welcome_login") {

        // Tela de Welcome com o campo de nome e botão para a proxima tela : player_profile
        composable("welcome_login") {
            WelcomeLoginScreen(navController = navController, viewModel = playerProfileViewModel)
        }
        // Tela de menu principal do jogador
        composable("player_profile/{playerTag}") { backStackEntry ->
            val playerTag = backStackEntry.arguments?.getString("playerTag") ?: ""
            PlayerProfile(navController = navController, playerTag = playerTag, viewModel = playerProfileViewModel)
        }

    }
}
