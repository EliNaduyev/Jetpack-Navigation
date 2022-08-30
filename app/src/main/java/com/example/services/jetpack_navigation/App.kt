package com.example.services.jetpack_navigation

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // declare modules
            androidContext(this@App)
            modules(viewModelModule, dbModels)
        }

    }
}