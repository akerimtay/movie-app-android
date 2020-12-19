package com.akerimtay.movieapp

import android.app.Application
import com.akerimtay.movieapp.di.apiModule
import com.akerimtay.movieapp.di.networkModule
import com.akerimtay.movieapp.di.repositoryModule
import com.akerimtay.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(viewModelModule, repositoryModule, networkModule, apiModule))
        }
    }
}