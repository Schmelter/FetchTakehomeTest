package com.example.fetchtakehometest.koin

import com.example.fetchtakehometest.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModules = module {
    viewModel {
        MainViewModel(androidApplication(), get())
    }
}