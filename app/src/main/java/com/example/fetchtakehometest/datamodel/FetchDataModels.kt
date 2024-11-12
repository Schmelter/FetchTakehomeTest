package com.example.fetchtakehometest.datamodel

import com.google.gson.annotations.SerializedName

data class FetchDataElement(
    @SerializedName("id") val id: Integer,
    @SerializedName("listId") val listId: Integer,
    @SerializedName("name") val name: String?
)