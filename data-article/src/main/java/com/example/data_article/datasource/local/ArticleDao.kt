package com.example.data_article.datasource.local

import androidx.room.*
import com.example.data_article.model.ArticleDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Transaction
    @Query("SELECT * FROM ArticleDTO")
    fun getArticleList(): Flow<List<ArticleDTO>?>

    @Transaction
    @Query("SELECT * FROM ArticleDTO WHERE id = :articleId")
    fun getArticle(articleId: Int): Flow<ArticleDTO?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleList(articleList: List<ArticleDTO>?)

    @Query("DELETE FROM ArticleDTO")
    suspend fun deleteArticleList()
}
