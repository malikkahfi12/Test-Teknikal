package com.learetechno.newsapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.ui.search.SearchViewModel

class SearchViewModelFactory(val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(newsRepository) as T
    }

}