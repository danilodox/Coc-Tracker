package com.br.moviefy.data.network


import ClanDetailsDto
import com.battlestats.wartracker.data.remote.model.ClanDto
import com.battlestats.wartracker.data.remote.model.PlayerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("clans/{clanTag}")
    suspend fun getClanDetails(@Path("clanTag") clanTag: String): ClanDetailsDto

    @GET("players/{playerTag}")
    suspend fun getPlayerDetails(
        @Path("playerTag") playerTag: String,
    ): PlayerDto
}