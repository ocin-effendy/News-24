package com.news.core.data.resource.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
data class NewsEntity (

    @ColumnInfo(name = "author")
    var author: String?,

    @PrimaryKey
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "url")
    var url: String?,

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String?,

    @ColumnInfo(name = "publishedAt")
    var publishedAt: String?,

    @ColumnInfo(name = "content")
    var content: String?,

)