package com.news.news24

import android.util.Log
import  androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.news.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.news.core.data.Resource
import com.news.core.data.resource.remote.response.ArticlesItem
import com.news.core.domain.model.News
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {

    lateinit var news: LiveData<Resource<List<News>>>


    fun getAllNews(query: String) {
        news = newsUseCase.getAllNews(query).asLiveData()
    }

    lateinit var newsData: LiveData<Resource<List<ArticlesItem>>>


    fun getDataNews(query: String) {
        newsData = newsUseCase.getDataNews(query).asLiveData()
    }
}

