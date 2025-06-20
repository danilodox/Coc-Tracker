package com.battlestats.wartracker.data.repository

import android.util.Log
import com.battlestats.wartracker.data.model.Player
import com.battlestats.wartracker.data.network.interceptor.TokenProvider
import com.br.moviefy.data.network.RetrofitService
import java.io.IOException

class PlayerRepositoryImpl(private val apiService: RetrofitService): PlayerRepository {

    //private val apiToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6Ijk3YTRiODMwLWY2ZjAtNDIwMy1hZTc5LWY2MGU3OTU3MDlkZCIsImlhdCI6MTc1MDQ1MzMyNiwic3ViIjoiZGV2ZWxvcGVyLzQxMTY1OGZmLWVjOGYtNzFkMS0wNzBkLTI3OTg3OWE2NGVlOCIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE4Ny4xOS4xMzEuMjMiXSwidHlwZSI6ImNsaWVudCJ9XX0.8KCZVn4py3E4uBpIHGIXqct-LXbZvQ8m8QLeOupHtACDAnz5Z3iEVWxYuZJ6n6ZbqjsI0VqFKijmL1Ip7048Fw"

    override suspend fun getPlayerDetails(playerTag: String): Player? {
        return try {
            val response = apiService.service.getPlayerDetails(playerTag/*, apiToken*/)
            Log.d("PlayerRepositoryImpl", "Jogador encontrado: $response")
            response
        }catch (e : Exception) {
            Log.e("PlayerRepositoryImpl", "Erro HTTP: ${e.cause} - ${e.message}")
            null
        } catch (e: IOException) {
            Log.e("PlayerRepositoryImpl", "Erro de conexão: ${e.message}")
            null
        }catch (e: Exception) {
            Log.e("PlayerRepositoryImpl", "Erro desconhecido: ${e.localizedMessage}")
            null

        }
    }



}