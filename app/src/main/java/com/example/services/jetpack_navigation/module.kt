package com.example.services.jetpack_navigation

import android.app.Application
import androidx.room.Room
import com.example.services.jetpack_navigation.canvas.CanvasViewModel
import com.example.services.jetpack_navigation.db.AppDatabase
import com.example.services.jetpack_navigation.db.DbViewModel
import com.example.services.jetpack_navigation.db.UserRepo
import com.example.services.jetpack_navigation.guide_line.GuidLineViewModel
import com.example.services.jetpack_navigation.home.HomeViewModel
import com.example.services.jetpack_navigation.settings.SettingsViewModel
import com.example.services.jetpack_navigation.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel() }
    viewModel { CanvasViewModel() }
    viewModel { GuidLineViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { DbViewModel(get()) }
}

val dbModels = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "MyAppDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) } //androidApplication required androidContext in App class
    factory { get<AppDatabase>().getUserDao() }
    factory { get<AppDatabase>().getUserItemDao() }
    single { UserRepo(get()) }
}