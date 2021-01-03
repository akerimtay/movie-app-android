package com.akerimtay.movieapp.di

import com.akerimtay.movieapp.data.network.datasource.CreditRemoteDataSource
import com.akerimtay.movieapp.data.network.datasource.MovieRemoteDataSource
import com.akerimtay.movieapp.data.repository.CreditRepository
import com.akerimtay.movieapp.data.repository.CreditRepositoryImpl
import com.akerimtay.movieapp.data.repository.MovieRepository
import com.akerimtay.movieapp.data.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<CreditRepository> { CreditRepositoryImpl(get(), get()) }
}

val dataSourceModule = module {
    single { MovieRemoteDataSource(get()) }
    single { CreditRemoteDataSource(get()) }
}