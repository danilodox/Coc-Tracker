package com.battlestats.wartracker.data.network.interceptor

import android.content.Context
import android.content.SharedPreferences

class TokenProvider(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    /*companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6Ijk3YTRiODMwLWY2ZjAtNDIwMy1hZTc5LWY2MGU3OTU3MDlkZCIsImlhdCI6MTc1MDQ1MzMyNiwic3ViIjoiZGV2ZWxvcGVyLzQxMTY1OGZmLWVjOGYtNzFkMS0wNzBkLTI3OTg3OWE2NGVlOCIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE4Ny4xOS4xMzEuMjMiXSwidHlwZSI6ImNsaWVudCJ9XX0.8KCZVn4py3E4uBpIHGIXqct-LXbZvQ8m8QLeOupHtACDAnz5Z3iEVWxYuZJ6n6ZbqjsI0VqFKijmL1Ip7048Fw"
    }*/

    companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "access_token" // apenas o nome da chave
    }

    fun saveToken(token: String) {
        prefs.edit().putString(PREF_KEY_ACCESS_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(PREF_KEY_ACCESS_TOKEN, null)
    }
}