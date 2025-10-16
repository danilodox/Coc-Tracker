package com.battlestats.wartracker.di

import androidx.room.Room
import com.battlestats.wartracker.data.datastore.SecureTokenProvider
import com.battlestats.wartracker.data.local.AppDatabase
import com.battlestats.wartracker.data.local.DatabaseKeyProvider
import com.battlestats.wartracker.domain.repository.PlayerRepository
import com.battlestats.wartracker.data.repository.PlayerRepositoryImpl
import com.br.moviefy.data.network.RetrofitService
import com.br.moviefy.data.network.interceptor.AuthInterceptor
import net.sqlcipher.database.SupportFactory
import org.koin.dsl.module

val dataModule = module {

    single { SecureTokenProvider(get()) }

    single { DatabaseKeyProvider(get()) }

    single {
        val passphrase = get<DatabaseKeyProvider>().getKey()
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "wartracker-db")
            .openHelperFactory(factory)
            .build()
    }

    single { get<AppDatabase>().playerDao() }

    single { AuthInterceptor(get()) }

    single { RetrofitService(get()) }

    single { get<RetrofitService>().service }

    single <PlayerRepository> {
        PlayerRepositoryImpl (
            apiService = get(),
            playerDao = get()
        )
    }



}