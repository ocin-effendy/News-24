package com.news.core.data.resource.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.news.core.data.resource.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getFavoriteNews(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToFavorite(news: NewsEntity)

    @Query("SELECT * FROM news where title = :titleNews")
    fun getSearchFavoriteNews(titleNews: String): Flow<NewsEntity>

    @Query("DELETE FROM news WHERE title = :titleNews")
    suspend fun deleteFavoriteNews(titleNews: String)
}