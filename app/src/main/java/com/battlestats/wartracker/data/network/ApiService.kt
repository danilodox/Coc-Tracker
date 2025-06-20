package com.br.moviefy.data.network


import com.battlestats.wartracker.data.model.Clan
import com.battlestats.wartracker.data.model.Player
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("clans/{clanTag}")
    suspend fun getClanDetails(@Path("clanTag") clanTag: String): Clan

    @GET("players/{playerTag}")
    suspend fun getPlayerDetails(
        @Path("playerTag") playerTag: String,
        //@Header("Authorization") token: String
    ): Player
}