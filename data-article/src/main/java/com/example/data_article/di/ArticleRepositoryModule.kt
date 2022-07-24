package com.example.data_article.di

import com.example.data_article.repository.ArticleRepositoryImpl
import com.example.domain_article.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ArticleRepositoryModule {

    @Binds
    @Singleton
    fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository


}