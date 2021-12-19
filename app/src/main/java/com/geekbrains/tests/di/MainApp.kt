package com.geekbrains.tests.di

import android.app.Application
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(main))
        }
    }

}