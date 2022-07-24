package com.example.domain_article.repository

import com.example.core.ApiResult
import com.example.domain_article.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticleListLocal(): Flow<List<ArticleEntity>?>

    fun getArticleLocal(articleId: Int): Flow<ArticleEntity?>

    suspend fun getArticleListRemote(page: Int): ApiResult<Unit>
}
