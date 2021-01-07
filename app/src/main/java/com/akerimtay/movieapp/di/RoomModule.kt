package com.akerimtay.movieapp.di

import android.content.Context
import com.akerimtay.movieapp.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    fun provideDatabase(context: Context) = AppDatabase.getDatabase(context)

    fun provideMoviesDao(appDatabase: AppDatabase) = appDatabase.movieDao()

    single { provideDatabase(androidContext()) }
    single { provideMoviesDao(get()) }
}