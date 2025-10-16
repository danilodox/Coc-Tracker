package com.battlestats.wartracker

import android.app.Application
import com.battlestats.wartracker.data.datastore.SecureTokenProvider
import com.battlestats.wartracker.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    private lateinit var secureTokenProvider: SecureTokenProvider

    override fun onCreate() {
        super.onCreate()

        secureTokenProvider = SecureTokenProvider(applicationContext)

        val apiTokenFromBuild = BuildConfig.API_KEY

        if (apiTokenFromBuild.isNotEmpty()) {
            secureTokenProvider.saveToken(apiTokenFromBuild)
        }

        startKoin {
            androidContext(this@MyApp)
            modules(appModules)
        }

    }

}