package com.example.data_article.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain_article.model.ArticleEntity

@Keep
@Entity
data class ArticleDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = ""
)


fun ArticleDTO.toArticleEntity():ArticleEntity{
    return ArticleEntity(this.id,
        this.author,
        this.title,
        this.description,
        this.url,
        this.urlToImage,
        this.publishedAt,
        this.content)
}
