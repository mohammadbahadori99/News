package com.example.data_article.model

import androidx.annotation.Keep

@Keep
data class ArticleListNetwork(
    val articles: List<ArticleDTO> = listOf()
)
