package com.battlestats.wartracker.data.local.model
import com.battlestats.wartracker.data.remote.model.ClanDto
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.battlestats.wartracker.data.remote.model.PlayerDto
import com.battlestats.wartracker.domain.model.Clan
import com.battlestats.wartracker.domain.model.Player

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val tag: String,
    val name: String,
    val expLevel: Int,
    val townHallLevel: Int,
    val clanName: String? = null
)

fun Player.toEntity(): PlayerEntity {
    return PlayerEntity(
        tag = this.tag,
        name = this.name,
        expLevel = this.expLevel,
        townHallLevel = this.townHallLevel,
        clanName = this.clan?.name
    )
}

fun PlayerEntity.toDomain(): Player {
    return Player(
        tag = this.tag,
        name = this.name,
        expLevel = this.expLevel,
        townHallLevel = this.townHallLevel,
        clan = this.clanName?.let { name ->
            Clan(tag = "", name = name, clanLevel = 0)
        }
    )
}

fun ClanDto.toDomain(): Clan {
    return Clan(
        tag = this.tag ?: "",
        name = this.name ?: "Unknown Clan",
        clanLevel = this.clanLevel ?: 0
    )
}


fun PlayerDto.toDomain(): Player {
    return Player(
        tag = this.tag ?: "",
        name = this.name ?: "Unknown Player",
        expLevel = this.expLevel ?: 0,
        townHallLevel = this.townHallLevel ?: 0,
        clan = this.clan?.toDomain()
    )
}



