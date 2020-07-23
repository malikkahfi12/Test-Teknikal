package com.learetechno.newsapp.model.articles


import com.google.gson.annotations.SerializedName

data class DataNews(
    @SerializedName("articles")
    val articles: MutableList<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)