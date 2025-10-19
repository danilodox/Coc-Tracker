package com.battlestats.wartracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.battlestats.wartracker.domain.model.Clan

@Entity(tableName = "clans")
data class ClanEntity(
    @PrimaryKey val tag: String,
    val name: String,
    val level: Int,
    val badgeUrl: String?
)

fun Clan.toEntity(): ClanEntity {
    return ClanEntity(
        tag = this.tag,
        name = this.name,
        level = this.clanLevel,
        badgeUrl = null // ou this.badgeUrl se você o tiver
    )
}