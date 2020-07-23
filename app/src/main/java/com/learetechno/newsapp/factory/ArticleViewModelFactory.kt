package com.learetechno.newsapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.ui.article.ArticleViewModel

class ArticleViewModelFactory( val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(newsRepository) as T
    }
}