package com.battlestats.wartracker.di

import LoginPlayerUseCase
import com.battlestats.wartracker.domain.usecase.CalculateTownHallProgressUseCase
import com.battlestats.wartracker.domain.usecase.GetPlayerProfileUseCase
import com.battlestats.wartracker.domain.usecase.ToggleFavoriteStatusUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { LoginPlayerUseCase(get(), get()) }
    factory { GetPlayerProfileUseCase(get()) }
    factory { ToggleFavoriteStatusUseCase(get()) }
    factory { CalculateTownHallProgressUseCase() }
}