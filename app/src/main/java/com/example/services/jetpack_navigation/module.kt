package com.example.services.jetpack_navigation

import com.example.services.jetpack_navigation.home.HomeViewModel
import com.example.services.jetpack_navigation.settings.SettingsViewModel
import com.example.services.jetpack_navigation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel() }
    viewModel { SettingsViewModel() }
}