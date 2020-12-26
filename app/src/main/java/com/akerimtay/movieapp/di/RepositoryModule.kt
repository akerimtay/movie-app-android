package com.akerimtay.movieapp.di

import com.akerimtay.movieapp.data.network.datasource.MovieRemoteDataSource
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.data.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}

val dataSourceModule = module {
    single { MovieRemoteDataSource(get()) }
}