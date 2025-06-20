package com.battlestats.wartracker.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.battlestats.wartracker.R
import com.battlestats.wartracker.ui.player_profile.PlayerProfileUiEvent
import com.battlestats.wartracker.ui.player_profile.PlayerProfileViewModel

@Composable
fun WelcomeLoginScreen(navController: NavController, viewModel: PlayerProfileViewModel) {
    var playerName by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState.isLoading

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is PlayerProfileUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is PlayerProfileUiEvent.NavigateToNextScreen -> {
                    // Aqui você pode decidir como navegar. Como você está usando o playerTag fixo,
                    // pode armazenar o último player carregado no state e navegar com base nele:
                    Log.d("aquii"," TAG = ${uiState.player?.tag}")
                    uiState.player?.tag?.let { tag ->
                        navController.navigate("player_profile/$tag")
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.clash_logo),
                contentDescription = "Clash of Clans Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Enter your Nickname",
                color = Color.Yellow,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Nickname") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Yellow,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Yellow
                ),
                textStyle = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator(color = Color.Yellow)
            } else {
                Button(
                    onClick = {
                        val tag = "#${playerName.text.trim()}" // Adiciona "#" ao nickname
                        //val tag = "#2PLVVQYRP" // futuramente você pode usar: "#${playerName.text.trim()}"
                        viewModel.getPlayerData(tag)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Confirm", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }
    }












    /*var playerName by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)), // Fundo escuro estilo Clash
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.clash_logo),
                contentDescription = "Clash of Clans Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Enter your Nickname",
                color = Color.Yellow,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Nickname") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Yellow,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Yellow
                ),
                textStyle = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator(color = Color.Yellow)
            } else {

                Button(
                    onClick = {
                        //val playerTag = "#${playerName.text.trim()}" // Adiciona "#" ao nickname
                        val playerTag = "#2PLVVQYRP" // to test
                        isLoading = true
                        Log.d("PlayerViewModel", "getFun Welcome  playTag = $playerTag")
                        viewModel.getPlayerData(playerTag, onSuccess = {
                            isLoading = false
                            navController.navigate("player_profile/$playerTag")
                        }, onError = {
                            isLoading = false
                            Toast.makeText(context, "Player not found!", Toast.LENGTH_LONG).show()
                        })
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)), // Cor dourada estilo Clash
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Confirm", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }
    }*/
}