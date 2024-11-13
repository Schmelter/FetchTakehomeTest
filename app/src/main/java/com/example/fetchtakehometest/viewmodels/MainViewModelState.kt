package com.example.fetchtakehometest.viewmodels

import com.example.fetchtakehometest.datamodel.FetchDataElement

data class MainViewModelState(
    val isLoading: Boolean = false,
    val fetchDataElements: List<FetchDataElement>? = null
)