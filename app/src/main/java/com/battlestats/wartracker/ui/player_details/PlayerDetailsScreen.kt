package com.battlestats.wartracker.ui.player_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.battlestats.wartracker.R
import com.battlestats.wartracker.domain.model.Hero
import com.battlestats.wartracker.domain.model.Player
import com.battlestats.wartracker.domain.model.Spell
import com.battlestats.wartracker.domain.model.Troop
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailsScreen(
    navController: NavController,
    viewModel: PlayerDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Player Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.DarkSectionBackground))
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.DarkGrayBackground))
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                uiState.error != null -> Text("Error: ${uiState.error}", color = Color.Red, modifier = Modifier.align(Alignment.Center))
                uiState.player != null -> PlayerDetailsContent(player = uiState.player!!)
            }
        }
    }
}


@Composable
fun PlayerDetailsContent(player: Player) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Seção "Herói"
        item {
            PlayerHeroCard(player = player)
        }
        // Grid de Estatísticas
        item {
            PlayerStatsGrid(player = player)
        }
        // Abas com mais detalhes
        item {
            DetailedInfoPager(player = player)
        }
    }
}

@Composable
fun PlayerHeroCard(player: Player) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.height(200.dp)
        ) {
            // Imagem de fundo (opcional, mas dá um toque premium)
            Image(
                painter = painterResource(id = R.drawable.background_art), // TODO: Adicione uma arte de fundo
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Gradiente para escurecer o fundo e dar contraste
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(0.2f), Color.Black.copy(0.8f))
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                AsyncImage(
//                    model = player.iconUrl, // Use a URL do ícone da sua API
//                    placeholder = painterResource(id = R.drawable.ic_player_avatar_placeholder),
//                    error = painterResource(id = R.drawable.ic_player_avatar_placeholder),
//                    contentDescription = "Player Avatar",
//                    modifier = Modifier
//                        .size(120.dp)
//                        .clip(CircleShape)
//                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = player.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = "LEVEL ${player.expLevel}", fontSize = 20.sp, color = colorResource(id = R.color.LightGold))
                    Text(text = player.tag, fontSize = 14.sp, color = colorResource(id = R.color.GrayText))
                }
            }
        }
    }
}

@Composable
fun PlayerStatsGrid(player: Player) {
    Column {
        Text("Stats", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatItem(iconId = R.drawable.clash_logo /*R.drawable.ic_trophy*/, value = player.trophies.toString(), label = "Trophies", modifier = Modifier.weight(1f))
            StatItem(iconId = R.drawable.clash_logo /*R.drawable.ic_war_star*/, value = player.warStars.toString(), label = "War Stars", modifier = Modifier.weight(1f)) // TODO: Adicione ic_war_star
            StatItem(iconId = R.drawable.clash_logo/*R.drawable.ic_donations*/, value = player.donations.toString(), label = "Donations", modifier = Modifier.weight(1f)) // TODO: Adicione ic_donations
        }
    }
}

@Composable
fun StatItem(iconId: Int, value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.DarkSectionBackground))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(painter = painterResource(id = iconId), contentDescription = label, modifier = Modifier.size(32.dp))
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = label, fontSize = 12.sp, color = colorResource(id = R.color.GrayText))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailedInfoPager(player: Player) {
    val tabTitles = listOf("Troops", "Heroes", "Spells")
    val pagerState = rememberPagerState { tabTitles.size }
    val scope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = colorResource(id = R.color.DarkSectionBackground),
            contentColor = colorResource(id = R.color.LightGold)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // Defina uma altura para o pager
        ) { page ->
            Box(modifier = Modifier.fillMaxSize().padding(top = 16.dp), contentAlignment = Alignment.Center) {
                when (page) {
                    0 -> TroopGrid(troops = player.troops)
                    1 -> HeroGrid(heroes = player.heroes)
                    2 -> SpellGrid(spells = player.spells)
                }
            }
        }
    }
}

@Composable
fun TroopGrid(troops: List<Troop>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 90.dp), // Cria colunas adaptáveis
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(troops) { troop ->
            GridItemCard(
                name = troop.name,
                level = troop.level,
                maxLevel = troop.maxLevel,
                placeholderIcon = R.drawable.clash_logo //R.drawable.ic_troop_placeholder // TODO: Crie um placeholder
            )
        }
    }
}

// NOVO COMPONENTE DE GRID PARA HERÓIS
@Composable
fun HeroGrid(heroes: List<Hero>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 90.dp),
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(heroes) { hero ->
            GridItemCard(
                name = hero.name,
                level = hero.level,
                maxLevel = hero.maxLevel,
                placeholderIcon = R.drawable.clash_logo //R.drawable.ic_hero_placeholder // TODO: Crie um placeholder
            )
        }
    }
}

@Composable
fun SpellGrid(spells: List<Spell>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 90.dp),
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(spells) { spell ->
            GridItemCard(
                name = spell.name,
                level = spell.level,
                maxLevel = spell.maxLevel,
                placeholderIcon = R.drawable.clash_logo  //R.drawable.ic_spell_placeholder // TODO: Crie um placeholder
            )
        }
    }
}

@Composable
fun GridItemCard(
    name: String,
    level: Int,
    maxLevel: Int,
    placeholderIcon: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.DarkSectionBackground))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
        ) {
//            AsyncImage(   MUDAR PARA IMAGE E CRIAR ICONS
//                model = iconUrl,
//                placeholder = painterResource(id = placeholderIcon),
//                error = painterResource(id = placeholderIcon),
//                contentDescription = name,
//                modifier = Modifier.fillMaxSize().padding(4.dp),
//                contentScale = ContentScale.Fit
//            )
            // Indicador de Nível
            LevelIndicator(level = level, maxLevel = maxLevel, modifier = Modifier.align(Alignment.BottomCenter))
        }
        Text(text = name, fontSize = 12.sp, color = Color.White, maxLines = 1)
    }
}

// NOVO COMPONENTE PARA O INDICADOR DE NÍVEL
@Composable
fun LevelIndicator(level: Int, maxLevel: Int, modifier: Modifier = Modifier) {
    val isMaxLevel = level == maxLevel
    val backgroundColor = if (isMaxLevel) colorResource(id = R.color.LightGold) else Color.Black.copy(alpha = 0.7f)
    val textColor = if (isMaxLevel) Color.Black else Color.White

    Box(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = "Lvl $level",
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}