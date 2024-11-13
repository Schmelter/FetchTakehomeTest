package com.example.fetchtakehometest.koin

import com.example.fetchtakehometest.api.FetchApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single<FetchApi> { provideFetchApi(get()) }
}

fun provideFetchApi(retrofit: Retrofit): FetchApi =
    retrofit.newBuilder().baseUrl("https://fetch-hiring.s3.amazonaws.com").build()
        .create(FetchApi::class.java)