package com.news.core.data

import com.news.core.data.resource.local.LocalDataSource
import com.news.core.data.resource.remote.RemoteDataSource
import com.news.core.data.resource.remote.network.ApiResponse
import com.news.core.data.resource.remote.response.ArticlesItem
import com.news.core.domain.model.News
import com.news.core.domain.repository.INewsRepository
import com.news.core.utils.AppExecutors
import com.news.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {

    override fun getDataNews(query: String): Flow<Resource<List<ArticlesItem>>> =
        object : NetworkResource() {
            override suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem>>> {
                return remoteDataSource.getAllNews(query)
            }
        }.asFlow()

    override fun getFavoriteNews(): Flow<List<News>> {
        return localDataSource.getFavoriteNews().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteNews(news: News) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute { localDataSource.setFavoriteNews(newsEntity) }
    }

    override fun searchFavoriteNews(title: String): Flow<News?> {
        return localDataSource.searchFavoriteNews(title).map {
            DataMapper.mapEntitiesToNews(it)
        }
    }

    override suspend fun deleteFavoriteNews(title: String) {
        localDataSource.deleteFavoriteNews(title)
    }
}