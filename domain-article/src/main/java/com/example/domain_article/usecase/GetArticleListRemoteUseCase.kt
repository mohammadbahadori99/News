package com.example.domain_article.usecase

import com.example.domain_article.repository.ArticleRepository
import javax.inject.Inject

class GetArticleListRemoteUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(page: Int) =
        articleRepository.getArticleListRemote(page = page)
}
