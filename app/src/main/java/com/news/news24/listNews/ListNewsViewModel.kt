package com.news.news24.listNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.news.core.data.Resource
import com.news.core.data.resource.remote.response.ArticlesItem
import com.news.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListNewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {

    lateinit var newsData: LiveData<Resource<List<ArticlesItem>>>


    fun getDataNews(query: String) {
        newsData = newsUseCase.getDataNews(query).asLiveData()
    }
}