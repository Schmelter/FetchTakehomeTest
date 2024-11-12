package com.example.fetchtakehometest.datamodel

sealed class ResultWrapper<out T : Any> {
    class Success<out T : Any>(val data: T?) : ResultWrapper<T>()
    data class GenericError(val exception: Exception? = null) : ResultWrapper<Nothing>()
    data class HttpStatusError(val exception: Exception? = null, val httpStatusCode: Int = 0) : ResultWrapper<Nothing>()
    data class NetworkError(val name: String? = null) : ResultWrapper<Nothing>()
    object AuthError : ResultWrapper<Nothing>()
}