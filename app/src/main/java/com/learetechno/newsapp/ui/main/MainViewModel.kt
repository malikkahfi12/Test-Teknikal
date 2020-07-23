package com.learetechno.newsapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learetechno.newsapp.model.articles.DataNews
import com.learetechno.newsapp.model.source.Data
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel (
    val newsRepository: NewsRepository
) : ViewModel() {
    val sourceNews : MutableLiveData<Resource<Data>> = MutableLiveData()
    val articleNewsHeadlines : MutableLiveData<Resource<DataNews>> = MutableLiveData()

    var sourceNewsResponse : Data? = null
    var articleNewsResponse : DataNews ? = null

    init {
        // Top Headlines params hardcode abc-news
        getTopHeadlines("us")
    }

    fun getSourceNews() = viewModelScope.launch {
        sourceNews.postValue(Resource.Loading())
        val response = newsRepository.getSourceNews()
        sourceNews.postValue(handleSourceNews(response))
    }

    fun getTopHeadlines(country : String) = viewModelScope.launch {
        articleNewsHeadlines.postValue(Resource.Loading())
        val response = newsRepository.getArticleHeadlines(country)
        articleNewsHeadlines.postValue(handleHeadlines(response))
    }

    private fun handleHeadlines(response: Response<DataNews>) : Resource<DataNews>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSourceNews(response : Response<Data>) : Resource<Data>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}