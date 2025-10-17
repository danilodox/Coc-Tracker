package com.battlestats.wartracker.domain.model

data class Player(
    val tag: String,
    val name: String,
    val expLevel: Int,
    val townHallLevel: Int,
    val clan: Clan? // Um jogador pode não ter clã, então é nulo
)

// Em: domain/model/Clan.kt
data class Clan(
    val tag: String,
    val name: String,
    val clanLevel: Int
)