package com.news.news24.favoriteNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.news.core.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteNewsViewModel@Inject constructor(newsUseCase: NewsUseCase) : ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews().asLiveData()
}