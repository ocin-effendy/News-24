package com.news.core.domain.usecase

import com.news.core.data.Resource
import com.news.core.data.resource.remote.response.ArticlesItem
import com.news.core.domain.model.News
import com.news.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val newsRepository: INewsRepository): NewsUseCase {

    override fun getAllNews(query: String) = newsRepository.getAllNews(query)

    override fun getDataNews(query: String) = newsRepository.getDataNews(query)

    override fun getFavoriteNews() = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: News, state: Boolean) = newsRepository.setFavoriteNews(news, state)

}