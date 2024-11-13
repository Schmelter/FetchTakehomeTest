package com.example.fetchtakehometest.datamodel

import com.google.gson.annotations.SerializedName

data class FetchDataElement(
    @SerializedName("id") val id: Int,
    @SerializedName("listId") val listId: Int,
    @SerializedName("name") val name: String?
)