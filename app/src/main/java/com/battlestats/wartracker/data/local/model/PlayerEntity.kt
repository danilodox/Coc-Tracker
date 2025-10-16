package com.battlestats.wartracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.battlestats.wartracker.data.remote.model.PlayerDto

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val tag: String,
    val name: String,
    val expLevel: Int,
    val townHallLevel: Int,
    val clanName: String? = null
)

fun PlayerDto.toEntity(): PlayerEntity = PlayerEntity(
    tag = this.tag ?: "",
    name = this.name ?: "",
    expLevel = this.expLevel ?: 0,
    townHallLevel = this.townHallLevel ?: 0,
    clanName = this.clan?.name
)

/*
fun PlayerEntity.toModel(): Player = Player(
    tag = this.tag,
    name = this.name,
    expLevel = this.expLevel,
    townHallLevel = this.townHallLevel,
    clan = this.clanName,

)*/
