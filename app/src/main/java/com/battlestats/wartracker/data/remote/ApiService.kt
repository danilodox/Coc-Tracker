package com.br.moviefy.data.network


import com.battlestats.wartracker.data.remote.model.Clan
import com.battlestats.wartracker.data.remote.model.PlayerDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("clans/{clanTag}")
    suspend fun getClanDetails(@Path("clanTag") clanTag: String): Clan

    @GET("players/{playerTag}")
    suspend fun getPlayerDetails(
        @Path("playerTag") playerTag: String,
        //@Header("Authorization") token: String
    ): PlayerDto
}