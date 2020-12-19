package com.akerimtay.movieapp.di

import com.akerimtay.movieapp.data.remote.datasource.MovieRemoteDataSource
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.data.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}

val dataSourceModule = module {
    single { MovieRemoteDataSource(get()) }
}