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
        tokenProvider.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6Ijk3YTRiODMwLWY2ZjAtNDIwMy1hZTc5LWY2MGU3OTU3MDlkZCIsImlhdCI6MTc1MDQ1MzMyNiwic3ViIjoiZGV2ZWxvcGVyLzQxMTY1OGZmLWVjOGYtNzFkMS0wNzBkLTI3OTg3OWE2NGVlOCIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE4Ny4xOS4xMzEuMjMiXSwidHlwZSI6ImNsaWVudCJ9XX0.8KCZVn4py3E4uBpIHGIXqct-LXbZvQ8m8QLeOupHtACDAnz5Z3iEVWxYuZJ6n6ZbqjsI0VqFKijmL1Ip7048Fw")

        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }

    }

}