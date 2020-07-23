package com.learetechno.newsapp.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learetechno.newsapp.model.articles.DataNews
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.util.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class ArticleViewModel(val newsRepository: NewsRepository) : ViewModel() {
    val article : MutableLiveData<Resource<DataNews>> = MutableLiveData()

    fun getSearchResult(q : String) = viewModelScope.launch {
        article.postValue(Resource.Loading())
        val response = newsRepository.getSearchResult(q)
        article.postValue(handleSearchResult(response))
    }

    private fun handleSearchResult(response: Response<DataNews>) : Resource<DataNews>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}