package com.news.core.data.resource.remote

import android.util.Log
import com.news.core.data.resource.remote.network.ApiResponse
import com.news.core.data.resource.remote.network.ApiService
import com.news.core.data.resource.remote.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllNews(query: String): Flow<ApiResponse<List<ArticlesItem>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getList("bc50da932b844e25be813c3ee1e4bebd",query)
                val dataArray = response.articles
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }

            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDataNews(query: String): Flow<ApiResponse<List<ArticlesItem>>>{
        return flow<ApiResponse<List<ArticlesItem>>> {
            try{
                val response = apiService.getList("bc50da932b844e25be813c3ee1e4bebd",query)
                val dataArray = response.articles
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.articles))
                    Log.i("flow", "MASUK KE FLOW REMOTE DATA SOURCE")
                } else {
                    emit(ApiResponse.Empty)
                }

            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())

            }
        }.flowOn(Dispatchers.IO)
    }
}