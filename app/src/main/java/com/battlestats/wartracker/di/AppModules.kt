package com.battlestats.wartracker.di

import org.koin.core.module.Module

val appModules: List<Module> = listOf( dataModule, uiModule, domainModule )