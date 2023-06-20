package com.news.core.data

import com.news.core.data.resource.remote.network.ApiResponse
import com.news.core.data.resource.remote.response.ArticlesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkResource {

    private var result: Flow<Resource<List<ArticlesItem>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data))
            }
            is ApiResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error<List<ArticlesItem>>(apiResponse.errorMessage))
            }

            else -> {}
        }
    }

    protected open fun onFetchFailed() {}
    protected abstract suspend fun createCall(): Flow<ApiResponse<List<ArticlesItem>>>

    fun asFlow(): Flow<Resource<List<ArticlesItem>>> = result
}