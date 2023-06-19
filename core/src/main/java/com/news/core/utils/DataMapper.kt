package com.news.core.utils

import com.news.core.data.resource.local.entity.NewsEntity
import com.news.core.data.resource.remote.response.ArticlesItem
import com.news.core.domain.model.News


object DataMapper {
    fun mapResponsesToEntities(input: List<ArticlesItem>): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val news = NewsEntity(
                author = it.author,
                title = it.title!!,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isFavorite = false
            )
            newsList.add(news)
        }
        return newsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                author = it.author ?: "",
                title = it.title ?: "",
                description = it.description ?: "",
                url = it.url ?: "",
                urlToImage = it.urlToImage ?: "",
                publishedAt = it.publishedAt ?: "",
                content = it.content ?: "",
                isFavorite = false
            )
        }

    fun mapDomainToEntity(input: News) = NewsEntity(
        author = input.author,
        title = input.title,
        description = input.description,
        url = input.url,
        urlToImage = input.urlToImage,
        publishedAt = input.publishedAt,
        content = input.content,
        isFavorite = false
    )
}