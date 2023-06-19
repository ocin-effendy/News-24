package com.news.core.data.resource.remote.network

import com.news.core.data.resource.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getList(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String
    ): NewsResponse
}