package com.example.fetchtakehometest.koin

import com.example.fetchtakehometest.api.FetchApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single { provideFetchApi(get()) }
}

fun provideFetchApi(retrofit: Retrofit): FetchApi =
    retrofit.newBuilder().baseUrl("https://fetch-hiring.s3.amazonaws.com").build()
        .create(FetchApi::class.java)