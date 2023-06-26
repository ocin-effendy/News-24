package com.news.core.di

import android.content.Context
import androidx.room.Room
import com.news.core.data.resource.local.room.NewsDao
import com.news.core.data.resource.local.room.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("news".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java, "News.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()
}