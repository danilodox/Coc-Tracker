package com.battlestats.wartracker.ui.player_profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.battlestats.wartracker.R
import com.battlestats.wartracker.data.remote.model.ClanDto
import com.battlestats.wartracker.data.remote.model.PlayerDto
import com.battlestats.wartracker.domain.model.Clan
import com.battlestats.wartracker.domain.model.Player
import com.battlestats.wartracker.ui.core.component.BottomBarNavigation
import com.battlestats.wartracker.ui.core.component.ClashProgressBar
import androidx.compose.material3.TabRow as PlayerClaTab

@Composable //#2PLVVQYRP
fun PlayerProfile(navController: NavController, playerTag: String, viewModel: PlayerProfileViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(playerTag) {
        if (playerTag.isNotBlank()) {
            viewModel.getPlayerData(playerTag)
        } else {
            Log.e("PlayerProfile", "Tag do jogador está vazia!")
        }
    }

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        uiState.isError -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Erro ao carregar jogador.", color = Color.Red)
            }
        }
        uiState.player != null -> {
            uiState.player?.let { player ->

                Scaffold(
                    bottomBar = {
                        BottomBarNavigation(navController = navController)
                    },
                    containerColor = Color(0xFF1E1E1E) // mesma cor de fundo da tela
                ) { innerPadding ->
                    Box (
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color(0xFF1E1E1E))
                            .padding(top = 16.dp)
                    ){
                        Column {
                            PlayerToolbar(
                                playerName = player.name ?: "Sem nome",
                                playerTag = player.tag ?: "",
                                //playerLevel =player.expLevel ?: 0,
                                isFavorite = uiState.isFavorite,
                                onBackClick = { navController.popBackStack() },
                                onFavoriteClick = { viewModel.onEvent(PlayerProfileUiEvent.OnFavoriteClick(player)) }
                            )

                            PlayerProfileContent(selectedTab, onTabSelected = {
                                selectedTab = it
                            }, player = player)
                        }

                    }
                }
            }
        }
    }
}



@Composable
private fun PlayerProfileContent(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    player: Player
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Tabs
        PlayerClaTab(
            selectedTabIndex = selectedTab,
            containerColor = colorResource(R.color.background_coc)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                text = {
                    Text(
                        "Jogador",
                        color = if (selectedTab == 0) Color.Yellow else Color.White
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                text = { Text("Clã", color = if (selectedTab == 1) Color.Yellow else Color.White) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .padding(8.dp)
                .clickable {},
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.background_card))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                PlayerLevelShield(player.expLevel)

                Column(modifier = Modifier.padding(8.dp)) {

                    Text(text = player.name, fontSize = 16.sp, color = Color.White)
                    Text(text = player.tag, fontSize = 14.sp, color = Color.White)

                }

            }

        }


        // Conteúdo da aba
        when (selectedTab) {
            0 -> PlayerInfoSection(player)
            1 -> ClanInfoSection(player.clan)
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), // Margem lateral
            thickness = 2.dp,
            color = Color(0xFFD4AF37) // Cor dourada estilo Clash
        )


        Card(
            modifier = Modifier
                .padding(8.dp)
                .background(colorResource(R.color.background_coc))
                .clickable {},
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.background_card)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    "Progresso",
                    color = Color.Yellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(colorResource(R.color.background_card))
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.background_card))
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                townHallLevel(player.townHallLevel)  // ajustar icone para centro de vila

                Column(modifier = Modifier.padding(8.dp)) {

                    Text(text = "Centro da vila", fontSize = 16.sp, color = Color.White)


                    // barra

                    ClashProgressBar(progress = 0.65f)

                    //Nesse card, podeira quando clicar, ir para outra tela
                    // e nela dividir em outros cards :
                    // herois
                    // construcoes
                    // defesas
                    // tropas
                    // muros

                }

            }




        }


    }
}

@Composable
fun PlayerInfoSection(player: Player?) {

    Column(modifier = Modifier.padding(8.dp)) {
      /*  player?.let {
            Text(text = "Level: ${it.expLevel}", color = Color.White, fontSize = 18.sp)
            Text(text = "Troféus: ${it.trophies}", color = Color.White, fontSize = 18.sp)
        } ?: Text(text = "Carregando informações...", color = Color.Gray)*/

        Card( //Heros info
            modifier = Modifier
                .padding(8.dp)
                .clickable {},
            elevation = CardDefaults.cardElevation(4.dp)
        ){

        }
        Card( //troops info
            modifier = Modifier
                .padding(8.dp)
                .clickable {},
            elevation = CardDefaults.cardElevation(4.dp)
        ){

        }

    }


}

@Composable
fun ClanInfoSection(clan: Clan?) {
    Column(modifier = Modifier.padding(16.dp)) {
        clan?.let {
            Text(text = "Nome do Clã: ${it.name}", color = Color.White, fontSize = 18.sp)
            Text(text = "Nível do Clã: ${it.clanLevel}", color = Color.White, fontSize = 18.sp)
        } ?: Text(text = "Nenhum clã encontrado", color = Color.Gray)
    }
}

@Composable
fun PlayerLevelShield(level: Int?) { //aquiii pedir a renata para criar o icone de experiencia igual do CoC
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp) // Ajuste o tamanho conforme necessário
            .background(brush = Brush.radialGradient(
                colors = listOf(Color(0xFF2A7FFF), Color(0xFF1E4EBF)), // Azul Clash of Clans
                radius = 50f
            ), shape = CircleShape)
            .border(3.dp, Color(0xFF0F2A66), CircleShape) // Borda escura
    ) {
        Text(
            text = level.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
@Composable
fun townHallLevel(level: Int?) { //aquiii pedir a renata para criar o icone de experiencia igual do CoC
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp) // Ajuste o tamanho conforme necessário
            .background(brush = Brush.radialGradient(
                colors = listOf(Color(0xFFC5C514), Color(0xFFC76F0D)), // Azul Clash of Clans
                radius = 50f
            ), shape = CircleShape)
            .border(3.dp, Color(0xFF69470E), CircleShape) // Borda escura
    ) {
        Text(
            text = level.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerToolbar(
    playerName: String,
    playerTag: String,
   // playerLevel: Int,
    isFavorite: Boolean = false,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1E1E1E), // Cor de fundo no estilo Clash
            titleContentColor = Color.Yellow
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_white_24), // Ícone customizado de voltar
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = playerName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Yellow)
                Text(text = playerTag, fontSize = 14.sp, color = Color.Gray)
            }
        },
        actions = {

            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }
        }
    )
}
