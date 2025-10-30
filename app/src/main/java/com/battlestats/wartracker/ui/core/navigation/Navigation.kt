package com.battlestats.wartracker.ui.core.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.battlestats.wartracker.ui.clan_info.ClanInfoScreen
import com.battlestats.wartracker.ui.clan_info.ClanInfoViewModel
import com.battlestats.wartracker.ui.favorite_player.FavoritePlayersScreen
import com.battlestats.wartracker.ui.favorite_player.FavoritePlayersViewModel
import com.battlestats.wartracker.ui.player_login.PlayerLoginViewModel
import com.battlestats.wartracker.ui.home.HomeScreen
import com.battlestats.wartracker.ui.player_login.WelcomeLoginScreen
import com.battlestats.wartracker.ui.home.HomeViewModel
import com.battlestats.wartracker.ui.player_details.PlayerDetailsScreen
import com.battlestats.wartracker.ui.player_details.PlayerDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    favoritePlayersViewModel: FavoritePlayersViewModel
) {
    NavHost(navController = navController, startDestination = "welcome_login") {

        composable("welcome_login") {
            val viewModel: PlayerLoginViewModel = koinViewModel()
            WelcomeLoginScreen(navController = navController, viewModel = viewModel)
        }
        // Home Screen
        composable(
            route = "home_screen/{playerTag}",
            arguments = listOf(navArgument("playerTag") { type = NavType.StringType })
        ){ backStackEntry ->
            val viewModel: HomeViewModel = koinViewModel()
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        //Favorites Screen
        composable("favorites") {
            FavoritePlayersScreen(navController = navController, viewModel = favoritePlayersViewModel)
        }

        composable(
            route = "clan_info/{clanTag}",
            arguments = listOf(navArgument("clanTag") { type = NavType.StringType })
        ) { backStackEntry ->
            val clanTag = Uri.decode(backStackEntry.arguments?.getString("clanTag") ?: "")
            val viewModel: ClanInfoViewModel = koinViewModel()
            ClanInfoScreen(
                navController = navController,
                clanTag = clanTag,
                viewModel = viewModel
            )
        }

        composable(
            route = "player_details/{playerTag}",
            arguments = listOf(navArgument("playerTag") { type = NavType.StringType })
        ) { backStackEntry ->
            val playerTag = backStackEntry.arguments?.getString("playerTag") ?: ""
            val viewModel: PlayerDetailsViewModel = koinViewModel()
            // A injeção do SavedStateHandle no ViewModel cuidará de pegar o playerTag
            PlayerDetailsScreen(navController = navController, viewModel = viewModel)
        }

        composable( //Mesma tela de player_details mas com os membros do clan
            route = "member_details/{playerTag}",
            arguments = listOf(navArgument("playerTag") { type = NavType.StringType })
        ) { backStackEntry ->
            val playerTag = backStackEntry.arguments?.getString("playerTag") ?: ""
            val viewModel: PlayerDetailsViewModel = koinViewModel()
            PlayerDetailsScreen(navController = navController, viewModel = viewModel)
        }
    }
}
