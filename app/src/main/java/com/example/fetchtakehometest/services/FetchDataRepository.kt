package com.example.fetchtakehometest.services

import com.example.fetchtakehometest.datamodel.FetchDataElement
import com.example.fetchtakehometest.datamodel.ResultWrapper

interface FetchDataRepository {

    suspend fun getFetchData(): ResultWrapper<List<FetchDataElement>>

}