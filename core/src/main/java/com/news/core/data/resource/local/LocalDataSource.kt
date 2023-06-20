package com.news.core.data.resource.local

import com.news.core.data.resource.local.entity.NewsEntity
import com.news.core.data.resource.local.room.NewsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalDataSource @Inject constructor(private val newsDao: NewsDao) {

    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getFavoriteNews()

    fun setFavoriteNews(news: NewsEntity) {
        newsDao.insertToFavorite(news)
    }

    fun searchFavoriteNews(title: String): Flow<NewsEntity> = newsDao.getSearchFavoriteNews(title)

    suspend fun deleteFavoriteNews(title: String) = newsDao.deleteFavoriteNews(title)

}