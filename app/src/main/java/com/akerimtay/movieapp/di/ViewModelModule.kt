package com.akerimtay.movieapp.di

import com.akerimtay.movieapp.ui.details.DetailsViewModel
import com.akerimtay.movieapp.ui.home.HomeViewModel
import com.akerimtay.movieapp.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
}