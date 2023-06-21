package com.news.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.news.core.domain.usecase.NewsUseCase
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(newsUseCase: NewsUseCase) : ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}