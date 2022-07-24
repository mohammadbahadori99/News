package com.example.data_article.di

import com.example.data_article.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ArticleModule {

    @Singleton
    @Provides
    fun provideArticleService(retrofit: Retrofit): com.example.data_article.datasource.remote.ArticleService {
        return retrofit.create(com.example.data_article.datasource.remote.ArticleService::class.java)
    }

    @Singleton
    @Provides
    fun provideArticleDao(appDb: AppDb): com.example.data_article.datasource.local.ArticleDao {
        return appDb.articleDao()
    }
}
