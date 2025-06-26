package com.battlestats.wartracker.di

import androidx.room.Room
import com.battlestats.wartracker.data.local.AppDatabase
import com.battlestats.wartracker.data.local.repository.LocalPlayerRepository
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "wartracker-db").build()
    }

    single { get<AppDatabase>().playerDao() }

    single { LocalPlayerRepository(get()) }
}