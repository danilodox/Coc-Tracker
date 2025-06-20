package com.battlestats.wartracker.di

import com.battlestats.wartracker.data.network.interceptor.TokenProvider
import com.battlestats.wartracker.data.repository.PlayerRepository
import com.battlestats.wartracker.data.repository.PlayerRepositoryImpl
import com.br.moviefy.data.network.RetrofitService
import com.br.moviefy.data.network.interceptor.AuthInterceptor
import org.koin.dsl.module

val dataModule = module {

    single { TokenProvider(get()) }

    single { AuthInterceptor(get()) }

    //factory { RetrofitService(get()) }
    single {
        RetrofitService(tokenProvider = get())
    }

    single <PlayerRepository> { PlayerRepositoryImpl (apiService = get()) }



}