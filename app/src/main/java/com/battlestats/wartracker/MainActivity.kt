package com.battlestats.wartracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.battlestats.wartracker.data.network.interceptor.TokenProvider
import org.koin.androidx.compose.koinViewModel
import com.battlestats.wartracker.ui.navigation.AppNavigation
import com.battlestats.wartracker.ui.player_login.PlayerLoginViewModel
import com.battlestats.wartracker.ui.player_profile.PlayerProfileViewModel

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val playerViewModel = koinViewModel<PlayerProfileViewModel>()
            val playerLoginViewModel = koinViewModel<PlayerLoginViewModel>()



            MaterialTheme {
                AppNavigation(navController = navController,
                    playerProfileViewModel = playerViewModel,
                    playerLoginViewModel = playerLoginViewModel
                )
            }
        }
    }
}