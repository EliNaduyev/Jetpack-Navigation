package com.example.services.jetpack_navigation

import android.app.Application
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // declare modules
            modules(viewModelModule)
        }

    }
}