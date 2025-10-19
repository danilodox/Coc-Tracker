package com.battlestats.wartracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.battlestats.wartracker.ui.favorite_player.FavoritePlayersViewModel
import org.koin.androidx.compose.koinViewModel
import com.battlestats.wartracker.ui.core.navigation.AppNavigation
import com.battlestats.wartracker.ui.player_login.PlayerLoginViewModel
import com.battlestats.wartracker.ui.home.HomeViewModel

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val favoritePlayersViewModel = koinViewModel<FavoritePlayersViewModel>()


            MaterialTheme {
                AppNavigation(navController = navController,
                    favoritePlayersViewModel = favoritePlayersViewModel
                )
            }
        }
    }
}