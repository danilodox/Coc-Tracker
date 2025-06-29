package com.battlestats.wartracker

import android.app.Application
import com.battlestats.wartracker.data.network.interceptor.TokenProvider
import com.battlestats.wartracker.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val tokenProvider = TokenProvider(this)
        tokenProvider.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImMzMjZiNjkxLTY0MGUtNDcwOS04Y2M0LWJiOTdjMTA1YWFkOSIsImlhdCI6MTc1MTExNDM0NCwic3ViIjoiZGV2ZWxvcGVyLzQxMTY1OGZmLWVjOGYtNzFkMS0wNzBkLTI3OTg3OWE2NGVlOCIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE4Ny4xOS4xMzEuMjA5Il0sInR5cGUiOiJjbGllbnQifV19.vuZwImwlBhboQ8k10sCXim3LqaTevLkIp8K9RqWgTKCeL1TXQSYADLLWJVEhxlQ6Lf3GhxH_oHYMd6G8nN0ALA")

        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }

    }

}