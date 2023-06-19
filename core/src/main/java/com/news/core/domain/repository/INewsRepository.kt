package com.news.core.domain.repository

import com.news.core.data.Resource
import com.news.core.data.resource.remote.response.ArticlesItem
import com.news.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository {

    fun getAllNews(query: String): Flow<Resource<List<News>>>

    fun getDataNews(query: String): Flow<Resource<List<ArticlesItem>>>

    fun getFavoriteNews(): Flow<List<News>>

    fun setFavoriteNews(news: News, state: Boolean)
}