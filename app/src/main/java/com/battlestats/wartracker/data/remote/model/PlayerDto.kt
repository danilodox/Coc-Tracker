package com.battlestats.wartracker.data.remote.model

data class PlayerDto(
    val league: League?,
    val builderBaseLeague: BuilderBaseLeague?,
    val clan: Clan?,
    val role: String?,
    val warPreference: String?,
    val attackWins: Int?,
    val defenseWins: Int?,
    val townHallLevel: Int?,
    val townHallWeaponLevel: Int?,
    val legendStatistics: LegendStatistics?,
    val troops: List<Troop>?,
    val heroes: List<Hero>?,
    val heroEquipment: List<HeroEquipment>?,
    val spells: List<Spell>?,
    val labels: List<Label>?,
    val tag: String?,
    val name: String?,
    val expLevel: Int?,
    val trophies: Int?,
    val bestTrophies: Int?,
    val donations: Int?,
    val donationsReceived: Int?,
    val builderHallLevel: Int?,
    val builderBaseTrophies: Int?,
    val bestBuilderBaseTrophies: Int?,
    val warStars: Int?,
    val achievements: List<Achievement>?,
    val clanCapitalContributions: Int?,
    val playerHouse: PlayerHouse?
)

data class League(
    val name: String?,
    val id: Int?,
    val iconUrls: Map<String, String>?
)

data class BuilderBaseLeague(
    val name: String?,
    val id: Int?
)

data class Clan(
    val tag: String?,
    val clanLevel: Int?,
    val name: String?,
    val badgeUrls: Map<String, String>?
)

data class LegendStatistics(
    val legendTrophies: Int?,
    val currentSeason: Season?,
    val bestSeason: Season?,
    val bestBuilderBaseSeason: Season?,
    val previousSeason: Season?,
    val previousBuilderBaseSeason: Season?
)

data class Season(
    val trophies: Int?,
    val id: String?,
    val rank: Int?
)

data class Troop(
    val level: Int?,
    val name: String?,
    val maxLevel: Int?,
    val village: String?,
    val superTroopIsActive: Boolean?,
    val equipment: List<String>?
)

data class Hero(
    val level: Int?,
    val name: String?,
    val maxLevel: Int?,
    val village: String?,
    val superTroopIsActive: Boolean?,
    val equipment: List<HeroEquipment>?
)

data class HeroEquipment(
    val level: Int?,
    val name: String?,
    val maxLevel: Int?,
    val village: String?,
    val superTroopIsActive: Boolean?,
    val equipment: List<String>?
)

data class Spell(
    val level: Int?,
    val name: String?,
    val maxLevel: Int?,
    val village: String?,
    val superTroopIsActive: Boolean?,
    val equipment: List<String>?
)

data class Label(
    val name: String?,
    val id: Int?,
    val iconUrls: Map<String, String>?
)

data class Achievement(
    val stars: Int?,
    val value: Int?,
    val name: String?,
    val target: Int?,
    val info: String?,
    val completionInfo: String?,
    val village: String?
)

data class PlayerHouse(
    val elements: List<PlayerHouseElement>?
)

data class PlayerHouseElement(
    val id: Int?,
    val type: String?
)