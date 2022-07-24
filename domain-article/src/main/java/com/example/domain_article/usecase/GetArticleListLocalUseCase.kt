package com.example.domain_article.usecase

import com.example.domain_article.repository.ArticleRepository
import javax.inject.Inject

class GetArticleListLocalUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    operator fun invoke() = articleRepository.getArticleListLocal()
}
