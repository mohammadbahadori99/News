package com.example.data_article.datasource.local

import androidx.room.withTransaction
import com.example.data_article.model.ArticleDTO
import com.example.data_article.model.toArticleEntity
import com.example.domain_article.model.ArticleEntity
import com.example.data_article.db.AppDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleLocalDataSource @Inject constructor(
    private val appDb: AppDb,
    private val articleDao: ArticleDao
) {
    fun getArticleList(): Flow<List<ArticleEntity>> {
        return articleDao.getArticleList().map { articleList ->
            articleList?.map { articleDTO ->
                articleDTO.toArticleEntity()
            } ?: emptyList()
        }
    }

    fun getArticle(articleId: Int): Flow<ArticleEntity> {
        return articleDao.getArticle(articleId).map {
            it?.toArticleEntity()!!
        }
    }

    suspend fun insertArticleList(
        page: Int,
        articleList: List<ArticleDTO>
    ) {
        appDb.withTransaction {
            if (page == 1) {
                articleDao.deleteArticleList()
            }
            articleDao.insertArticleList(articleList)
        }
    }
}
