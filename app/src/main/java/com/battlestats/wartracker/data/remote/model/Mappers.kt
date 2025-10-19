package com.battlestats.wartracker.data.remote.model

import ClanDetailsDto
import ClanMemberDto
import com.battlestats.wartracker.domain.model.Clan
import com.battlestats.wartracker.domain.model.ClanDetails
import com.battlestats.wartracker.domain.model.ClanMember
import com.battlestats.wartracker.domain.model.Player

fun PlayerDto.toDomain(): Player {
    return Player(
        tag = this.tag ?: "",
        name = this.name ?: "Unknown Player",
        expLevel = this.expLevel ?: 0,
        townHallLevel = this.townHallLevel ?: 0,
        clan = this.clan?.toDomain()
    )
}

/**
 * Converte o DTO 'ClanDto' da API para o modelo de Domínio 'Clan'.
 */
fun ClanDto.toDomain(): Clan {
    return Clan(
        tag = this.tag ?: "",
        name = this.name ?: "Unknown Clan",
        clanLevel = this.clanLevel ?: 0
    )
}

fun ClanDetailsDto.toDomain(): ClanDetails {
    return ClanDetails(
        tag = this.tag,
        name = this.name,
        level = this.clanLevel,
        badgeUrl = this.badgeUrls?.medium,
        membersCount = this.members,
        warWins = this.warWins,
        warLosses = this.warLosses ?: 0,
        warTies = this.warTies ?: 0,
        members = this.memberList?.map { it.toDomain() } ?: emptyList()
    )
}

/**
 * Converte o DTO 'ClanMemberDto' da API para o modelo de Domínio 'ClanMember'.
 */
fun ClanMemberDto.toDomain(): ClanMember {
    return ClanMember(
        tag = this.tag,
        name = this.name,
        townHallLevel = this.townHallLevel,
        expLevel = this.expLevel,
        trophies = this.trophies,
    )
}