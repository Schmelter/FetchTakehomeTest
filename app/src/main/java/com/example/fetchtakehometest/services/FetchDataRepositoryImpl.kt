package com.example.fetchtakehometest.services

import com.example.fetchtakehometest.api.FetchApi
import com.example.fetchtakehometest.datamodel.FetchDataElement
import com.example.fetchtakehometest.datamodel.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class FetchDataRepositoryImpl(
    private val fetchApi: FetchApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): FetchDataRepository {

    override suspend fun getFetchData(): ResultWrapper<List<FetchDataElement>> {
        return withContext(dispatcher) {
            try {
                val result = fetchApi.getFetchData()
                if (result.isSuccessful) {
                    result.body()?.let {
                        ResultWrapper.Success(result.body())
                    } ?: run {
                        // Empty response?
                        ResultWrapper.GenericError(Exception("No Data Returned"))
                    }
                } else {
                    ResultWrapper.HttpStatusError(Exception("Unknown Http Status Failure"), result.code())
                }
            } catch (ex: Exception) {
                ResultWrapper.GenericError(ex)
            }
        }
    }
}