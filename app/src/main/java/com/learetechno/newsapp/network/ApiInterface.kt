package com.learetechno.newsapp.network

import com.learetechno.newsapp.model.articles.DataNews
import com.learetechno.newsapp.model.source.Data
import com.learetechno.newsapp.util.Contants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{

    @GET("sources")
    suspend fun getSourceNews(
        @Query(value = "apiKey") apiKey : String = API_KEY
    ) : Response<Data>

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query(value = "country") key : String,
        @Query(value = "apiKey") apiKey: String = API_KEY
    ) : Response<DataNews>

    @GET("everything")
    suspend fun getSearchResult(
        @Query(value = "q") key: String,
        @Query(value = "apiKey") apiKey: String = API_KEY
    ) : Response<DataNews>

}