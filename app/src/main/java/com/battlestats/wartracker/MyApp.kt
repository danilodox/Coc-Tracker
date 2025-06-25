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
        tokenProvider.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjAzYjAzNTU4LTkxOGQtNDk0Yy05MzMyLWYwMTMwZTc3MDA0NCIsImlhdCI6MTc1MDg3Njc2Nywic3ViIjoiZGV2ZWxvcGVyLzQxMTY1OGZmLWVjOGYtNzFkMS0wNzBkLTI3OTg3OWE2NGVlOCIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE4Ny4xOS4xMzEuMTE5Il0sInR5cGUiOiJjbGllbnQifV19.SMlSPwO_EQj_N8Bxf9HAethmJ14l801Wzox8hV9w71eKcRA1mCCX1mW2r4sK-x7BD6TiBjchiRzwk9fdXbZaTg")

        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }

    }

}