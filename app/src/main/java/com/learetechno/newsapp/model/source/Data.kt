package com.learetechno.newsapp.model.source


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("sources")
    val sources: MutableList<Source>,
    @SerializedName("status")
    val status: String
)