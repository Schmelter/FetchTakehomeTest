package com.example.fetchtakehometest.viewmodels

import com.example.fetchtakehometest.datamodel.FetchDataElement

data class MainViewModelState(
    val fetchDataElements: List<FetchDataElement>? = null
)