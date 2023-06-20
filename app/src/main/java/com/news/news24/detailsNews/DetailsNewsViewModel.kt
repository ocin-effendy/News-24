package com.news.news24.detailsNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.news.core.domain.model.News
import com.news.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsNewsViewModel@Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    fun setFavoriteNews(news: News) =
        newsUseCase.setFavoriteNews(news)

    fun searchFavoriteNews(title: String) = newsUseCase.searchFavoriteNews(title).asLiveData()

    fun deleteFavoriteNews(title: String) {
        viewModelScope.launch {
            newsUseCase.deleteFavoriteNews(title)
        }
    }
}