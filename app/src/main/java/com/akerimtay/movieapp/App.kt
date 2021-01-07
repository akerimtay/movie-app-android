package com.akerimtay.movieapp

import android.app.Application
import com.akerimtay.movieapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    companion object {
        private lateinit var INSTANCE: App

        @Synchronized
        fun getInstance(): App = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    networkModule,
                    apiModule,
                    dataSourceModule,
                    roomModule
                )
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}