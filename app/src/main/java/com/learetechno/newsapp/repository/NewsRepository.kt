package com.learetechno.newsapp.repository

import com.learetechno.newsapp.network.RetrofitInstances

class NewsRepository {
    suspend fun getSourceNews() = RetrofitInstances.api.getSourceNews()
    suspend fun getArticleHeadlines(country : String) = RetrofitInstances.api.getTopHeadlines(country)
    suspend fun getSearchResult(q : String) = RetrofitInstances.api.getSearchResult(q)
}