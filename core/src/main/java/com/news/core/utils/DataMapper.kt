package com.news.core.utils

import com.news.core.data.resource.local.entity.NewsEntity
import com.news.core.domain.model.News


object DataMapper {

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
        input.map {
            News(
                author = it.author ?: "",
                title = it.title,
                description = it.description ?: "",
                url = it.url ?: "",
                urlToImage = it.urlToImage ?: "",
                publishedAt = it.publishedAt ?: "",
                content = it.content ?: "",
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
    )

    fun mapEntitiesToNews(input: NewsEntity?): News? =
        input?.let {
            News(
                author = input.author ?: "no author",
                title = it.title,
                description = input.description ?: "no description",
                url = input.url ?: "no url",
                urlToImage = input.urlToImage ?: "no urlImage",
                publishedAt = input.publishedAt ?: "no published",
                content = input.content ?: "no content",
            )
        }

}