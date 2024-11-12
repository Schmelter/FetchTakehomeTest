package com.example.fetchtakehometest.koin

import com.example.fetchtakehometest.services.FetchDataRepository
import com.example.fetchtakehometest.services.FetchDataRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val respositoryModule = module {

    single { FetchDataRepositoryImpl(get(), Dispatchers.IO) }
}