package com.example.fetchtakehometest.api

import com.example.fetchtakehometest.datamodel.FetchDataElement
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface FetchApi {
    @Headers("Accept: application/json", "Content-Type: application/json", "No-Auth-Header: true")
    @GET("/hiring.json")
    suspend fun getFetchData(
    ): Response<List<FetchDataElement>>
}