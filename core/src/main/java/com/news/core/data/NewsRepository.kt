package com.news.core.data

import android.util.Log
import com.news.core.data.resource.local.LocalDataSource
import com.news.core.data.resource.remote.RemoteDataSource
import com.news.core.data.resource.remote.network.ApiResponse
import com.news.core.data.resource.remote.response.ArticlesItem
import com.news.core.domain.model.News
import com.news.core.domain.repository.INewsRepository
import com.news.core.utils.AppExecutors
import com.news.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : INewsRepository {


    override fun getAllNews(query: String): Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, List<ArticlesItem>>() {

            override fun loadFromDB(): Flow<List<News>> {
                Log.e("cok","LOCAL JANCOKK JANCOKK")
                return localDataSource.getAllNews().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<News>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem>>> {
                Log.e("cok","INTERNET ASUU ASUUUU")

                return remoteDataSource.getAllNews(query)


            }

            override suspend fun saveCallResult(data: List<ArticlesItem>) {
                Log.e("cok","SAVE TAEKKKKKKK")
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asFlow()

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

    override fun setFavoriteNews(news: News, state: Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute { localDataSource.setFavoriteNews(newsEntity, state) }
    }
}