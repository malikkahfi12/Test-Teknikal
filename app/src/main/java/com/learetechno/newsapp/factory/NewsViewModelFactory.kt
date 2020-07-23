package com.learetechno.newsapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learetechno.newsapp.repository.NewsRepository
import com.learetechno.newsapp.ui.main.MainViewModel

class NewsViewModelFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(newsRepository) as T
    }

}