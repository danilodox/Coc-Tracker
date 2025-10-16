package com.battlestats.wartracker.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.battlestats.wartracker.ui.favorite_player.FavoritePlayersScreen
import com.battlestats.wartracker.ui.favorite_player.FavoritePlayersViewModel
import com.battlestats.wartracker.ui.player_login.PlayerLoginViewModel
import com.battlestats.wartracker.ui.player_profile.PlayerProfile
import com.battlestats.wartracker.ui.player_login.WelcomeLoginScreen
import com.battlestats.wartracker.ui.player_profile.PlayerProfileViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    playerProfileViewModel: PlayerProfileViewModel,
    playerLoginViewModel: PlayerLoginViewModel,
    favoritePlayersViewModel: FavoritePlayersViewModel
) {
    NavHost(navController = navController, startDestination = "welcome_login") {

        // Welcome Screen with Login Button to navigate to Player Profile Screen
        composable("welcome_login") {
            WelcomeLoginScreen(navController = navController, viewModel = playerLoginViewModel)
        }
        // Player Profile Screen
        composable("player_profile/{playerTag}") { backStackEntry ->
            val playerTag = backStackEntry.arguments?.getString("playerTag") ?: ""
            PlayerProfile(navController = navController, playerTag = playerTag, viewModel = playerProfileViewModel)
        }

        //Favorites Screen
        composable("favorites") {
            FavoritePlayersScreen(navController = navController, viewModel = favoritePlayersViewModel)
        }
    }
}
