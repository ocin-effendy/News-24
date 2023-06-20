package com.news.core.domain.usecase

import com.news.core.domain.model.News
import com.news.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(private val newsRepository: INewsRepository): NewsUseCase {

    override fun getDataNews(query: String) = newsRepository.getDataNews(query)

    override fun getFavoriteNews() = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: News) = newsRepository.setFavoriteNews(news)

    override fun searchFavoriteNews(title: String): Flow<News?> =  newsRepository.searchFavoriteNews(title)
    override suspend fun deleteFavoriteNews(title: String) = newsRepository.deleteFavoriteNews(title)


}