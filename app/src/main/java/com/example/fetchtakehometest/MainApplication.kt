package com.example.fetchtakehometest

import android.app.Application
import com.example.fetchtakehometest.koin.apiModule
import com.example.fetchtakehometest.koin.appModule
import com.example.fetchtakehometest.koin.networkModule
import com.example.fetchtakehometest.koin.respositoryModule
import com.example.fetchtakehometest.koin.viewModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start koin and load core modules
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)

            modules(
                listOf(
                    appModule,
                    networkModule,
                    apiModule,
                    respositoryModule,
                    viewModules
                )
            )
        }
    }
}